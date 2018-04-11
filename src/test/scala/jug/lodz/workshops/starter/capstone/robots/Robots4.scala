package jug.lodz.workshops.starter.capstone.robots

import jug.lodz.workshops.starter.capstone.robots.Robots4.PunchGenerator
import org.scalatest.{FunSuite, MustMatchers}


object Robots4 {

  trait PunchGenerator{
    def generatePunch: () => Int
  }


  object PunchGenerator{
    private class ConstantPunchGenerator(hit:Int) extends PunchGenerator {
      override def generatePunch : () => Int =  ???
    }

    private class RandomPuchGenerator(upTo:Int) extends PunchGenerator{
      override def generatePunch: () =>  Int = ???
    }

    val const : Int => PunchGenerator = ???
    val random : Int => PunchGenerator = ???
  }

  trait Shield{
    def absorb : Int => Int
  }

  object Shield{


    val createShield : Int => Shield = ???
    val noShield : () => Shield = ???

    private class ChargedShield(initialEnergy:Int) extends Shield{
      private var currentEnergy=initialEnergy

      override def absorb: Int => Int = hit =>
        if(currentEnergy-hit <= 0){
          val damage=hit-currentEnergy
          currentEnergy = 0
          damage
        }else{
          currentEnergy=currentEnergy-hit
          0
        }
    }


    private object NoShield extends Shield{
      override def absorb : Int=>Int = ???
    }



  }


  class Robot4 private(initialEnergy:Int,shield: Shield,punchGenerator: PunchGenerator){
    private var energy:Int=initialEnergy

    val isAlive : () =>  Boolean = ???
    val energyStatus:() => Int = ???

    private val absorb : Int => Int = ???
    private val reduceEnergy : Int =>Unit = ???

    val takeHit : Int => Unit = absorb andThen reduceEnergy

    //between 0-25
    def punch : Int = punchGenerator.generatePunch()

  }

  object Robot4{

    def apply(initialEnergy:Int, shield:Shield,punchGenerator: PunchGenerator) ={
      require(initialEnergy>0, "initital energy must be greater than 0")
      new Robot4(initialEnergy,shield,punchGenerator)
    }

    val createDefault : Int => Robot4 = initialEnergy => new Robot4(initialEnergy,Shield.noShield(),PunchGenerator.const(10))
    val battle: (Robot4,Robot4) => Unit = ???
  }

}


class Robot4Test extends FunSuite with MustMatchers {
  test("No shield is not protecting at all"){
    Robots4.Shield.noShield().absorb(10) mustBe 10
    Robots4.Shield.noShield().absorb(10) mustBe 10
    Robots4.Shield.noShield().absorb(10) mustBe 10
  }

  test("Standard shield should absorb hits"){
      val shield=Robots4.Shield.createShield(100)

      shield.absorb(40) mustBe 0
      shield.absorb(40) mustBe 0
      shield.absorb(40) mustBe 20
      shield.absorb(40) mustBe 40
  }

  test("constant generator should always return teh same value"){
    val gen1=PunchGenerator.const(10)
    val gen2=PunchGenerator.const(20)

    gen1.generatePunch() mustBe 10
    gen1.generatePunch() mustBe 10
    gen1.generatePunch() mustBe 10

    gen2.generatePunch() mustBe 20
    gen2.generatePunch() mustBe 20
    gen2.generatePunch() mustBe 20
  }


  test("random generator returns value from range") {
    val genTo10=PunchGenerator.random(10)
    val genTo70=PunchGenerator.random(70)
    (1 to 10).foreach{_ =>
      genTo10.generatePunch() must be >=0
      genTo10.generatePunch() must be <=10
      genTo70.generatePunch() must be <=70
    }
  }


  test("robot should generate punch between 0 and 50"){
    val robot = Robots4.Robot4(100,Robots4.Shield.noShield(),PunchGenerator.const(10))

      robot.punch mustBe 10
      robot.punch mustBe 10
      robot.punch mustBe 10
  }

  test("shield absorbs energy"){
    val robot=Robots4.Robot4(70,Robots4.Shield.createShield(10),PunchGenerator.const(10))
    robot.energyStatus() mustBe 70
    robot.takeHit(15)
    robot.energyStatus() mustBe 65
  }


  test("robots battle"){
    val robot1=Robots4.Robot4(70,Robots4.Shield.createShield(50),PunchGenerator.const(20))
    val robot2=Robots4.Robot4(70,Robots4.Shield.createShield(50),PunchGenerator.const(10))

    Robots4.Robot4.battle(robot1,robot2)
    robot2.energyStatus() mustBe 70

    Robots4.Robot4.battle(robot1,robot2)
    Robots4.Robot4.battle(robot1,robot2)
    robot2.energyStatus() mustBe 60
  }

  test("Robot with positive energy should be alive") {
    Robots4.Robot4.createDefault(20).isAlive() mustBe true
    Robots4.Robot4.createDefault(1).isAlive() mustBe true
    Robots4.Robot4.createDefault(200).isAlive() mustBe true
  }

  test("Robot initial energy should be greater than 0") {
    an[IllegalArgumentException] must be thrownBy Robots4.Robot4(0,Robots4.Shield.noShield(),PunchGenerator.const(10))
    an[IllegalArgumentException] must be thrownBy Robots4.Robot4(-1,Robots4.Shield.noShield(),PunchGenerator.const(10))
    an[IllegalArgumentException] must be thrownBy Robots4.Robot4(-100,Robots4.Shield.noShield(),PunchGenerator.const(10))
  }

  test("hit robot"){
    val robot=Robots4.Robot4.createDefault(100)

    robot.takeHit(30)
    val firstEnergyStatus=robot.energyStatus()
    robot.takeHit(45)
    val secondEnergyStatus=robot.energyStatus()
    robot.takeHit(100)
    val thirdEnergyStatus=robot.energyStatus()

    firstEnergyStatus mustBe 70
    secondEnergyStatus mustBe 25
    thirdEnergyStatus mustBe 0

  }

}
