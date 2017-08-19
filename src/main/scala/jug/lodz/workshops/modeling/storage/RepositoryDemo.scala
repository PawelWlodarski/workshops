package jug.lodz.workshops.modeling.storage

import cats.data.Reader
import jug.lodz.workshops.WorkshopDisplayer
import monocle.macros.GenLens

import scala.util.{Failure, Success, Try}

object RepositoryDemo  extends WorkshopDisplayer{

  def main(args: Array[String]): Unit = {
    header("Repositories")

    //DECLARE REPOSITORY
    val meetupRepository : MeetupRepository = MeetupRepositoryInMemory
    val abstractRepository : Repository[Meetup,Topic] = MeetupRepositoryInMemory

    // QUERY ANd CHANGE ONE MEETUP
    val queriedJava9: Try[Meetup] =abstractRepository.query("Java 9")

    val topicLens = GenLens[Meetup](_.date)

    //!!! queriedJava9 is Try[Meetup] so normally we have to chandle Failure case
    val chanagedJava9 =queriedJava9.map{m => topicLens.modify(_ + " 18:00")(m)}


    //STORE CHANGED DATA
    chanagedJava9 match {
      case Success(m) =>section("result of storing data",abstractRepository.store(m))
      case Failure(e) => throw e
    }

    //DOMAIN OPERATION
    section("Domain operation",meetupRepository.startsOn("01.09.2017 18:00"))

    header("FUNCTIONAL DEPENDENCY INJECTION")

    //////////////////////////
    ////Repository Concept ///
    /////////////////////////

    //show implementation

    //////////////////////////
    ////Repository Program ///
    /////////////////////////

    val topic = "using Reader"
    val draft: Reader[MeetupRepository, Try[Meetup]] =ComposableMeetupService.draft(topic,"Piotrkowska 100")


    val scheduled=draft.flatMap{_ =>
      ComposableMeetupService.schedule(topic,"01.10.2017")
    }

    val result=scheduled.run(MeetupRepositoryInMemory)
    section("Successfully scheduled meetup",result)
    section("Successfully scheduled meetup from repo",MeetupRepositoryInMemory.query(topic))


    ////alternatively
    val topic2="Reader with For comprehension"

    val scheduled2=for{
      _ <- ComposableMeetupService.draft(topic2,"Piotrkowska 200")
      finalReader <- ComposableMeetupService.schedule(topic2,"01.10.2018")
    } yield finalReader

    val result2=scheduled2.run(MeetupRepositoryInMemory)

    section("Successfully scheduled meetup - for Comprehension",result2)
    section("Successfully scheduled meetup from repo - for Comprehension",MeetupRepositoryInMemory.query(topic2))

  }


  ///////////////////////////////
  /////REPOSITORY PART///////////
  ///////////////////////////////

  trait Repository[A, IdType] {
    def query(id: IdType): Try[A]  //or Try[Option[A]]
    def store(a: A): Try[A]
  }

  type Topic= String
  type Address=String
  type Date=String

  case class Meetup(topic:Topic,address:Address,date:Date)

  //Ads more domain operations
  trait MeetupRepository extends Repository[Meetup,Topic]{
    def store(m:Meetup) : Try[Meetup]
    def startsOn(when:Date) : Seq[Meetup]

    def where(topic:Topic) : Try[Address] = query(topic) match {  //or just query(topic).map(_.topic)
      case Success(meetup) => Success(meetup.topic)
      case Failure(ex) => Failure(ex)
    }
  }


  object MeetupRepositoryInMemory extends  MeetupRepository{

    private var data:Map[Topic,Meetup]= Map(
      "Java 9" -> Meetup("Java 9","Politechniki 34","01.09.2017"),
      "Scala 3" -> Meetup("Scala 3","Politechniki 34","03.09.2017"),
      "FP i DDD" -> Meetup("FP i DDD","Politechniki 34","01.09.2017")
    )

    override def store(m: Meetup): Try[Meetup] =  {
      data = data + (m.topic -> m)
      Success(m)
    }

    override def startsOn(when: Date): Seq[Meetup] = data.values.filter(_.date==when).toSeq
    override def query(id: Topic): Try[Meetup] = Try{
      data.getOrElse(id, throw new RuntimeException(s"missing meetup with topic $id"))
    }
  }

  ////////////////////////////////////
  ///// DEPENDENCY INJECTION PART ////
  ////////////////////////////////////


  trait MeetupeService1 {
    def draft(topic: Topic,address: Address,repo : MeetupRepository) : Try[Meetup]
    def schedule(topic : Topic, date:Date,repo : MeetupRepository) : Try[Meetup]
    def cancel(topic : Topic,repo : MeetupRepository) : Unit
  }

  object NonComposableMeetupService extends MeetupeService1{
    override def draft(topic: Topic, address: Address, repo: MeetupRepository): Try[Meetup] = ???
    override def schedule(topic: Topic, date: Date, repo: MeetupRepository): Try[Meetup] = ???
    override def cancel(topic: Topic, repo: MeetupRepository): Unit = ???
  }

  object ProgramUsingNonComposableMeetupService {
    val repo = MeetupRepositoryInMemory
    val service = NonComposableMeetupService
    def schedule(topic:Topic,address: Address,date:Date): Try[Meetup] = for{
      _ <- service.draft(topic,address,repo)
      meetup <- service.schedule(topic,date,repo)
    } yield meetup
  }


  /////////////////////////
  //////// READER ////////
  ///////////////////////
  import cats.data.Reader


  trait MeetupServiceReader {
    protected lazy val EmptyData = ""

    def draft(topic: Topic,address: Address) : Reader[MeetupRepository,Try[Meetup]]
    def schedule(topic : Topic, date:Date) : Reader[MeetupRepository,Try[Meetup]]
    def cancel(topic : Topic) : Reader[MeetupRepository,Try[Meetup]]
  }

  object ComposableMeetupService extends MeetupServiceReader{

    lazy val dateLens=GenLens[Meetup](_.date)

    override def draft(topic: Topic, address: Address): Reader[MeetupRepository, Try[Meetup]] =
    Reader{repository =>
      val meetupDraft=Meetup(topic,address,EmptyData)
      repository.store(meetupDraft)
    }
    override def schedule(topic: Topic, date: Date): Reader[MeetupRepository, Try[Meetup]] =
      Reader{repository =>
        val changedMeetup: Try[Meetup] =repository
          .query(topic)
          .map(meetup => dateLens.set(date)(meetup))

        //some other combinators are available
        changedMeetup.foreach(repository.store)

        changedMeetup
      }



    override def cancel(topic: Topic): Reader[MeetupRepository,Try[Meetup]] = ???
  }

}