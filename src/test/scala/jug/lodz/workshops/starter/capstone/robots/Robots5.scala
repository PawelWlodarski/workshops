package jug.lodz.workshops.starter.capstone.robots

import jug.lodz.workshops.starter.capstone.robots.Robots5._
import org.scalatest.{FunSuite, MustMatchers}

import scala.util.Random

object Robots5 {

  trait PunchGenerator{
    def generatePunch: () => Int
  }


  object PunchGenerator{
    private class ConstantPunchGenerator(hit:Int) extends PunchGenerator {
      override def generatePunch : () => Int =  () => hit
    }

    private class RandomPuchGenerator(upTo:Int) extends PunchGenerator{
      override def generatePunch: () =>  Int = () => Random.nextInt(upTo)
    }

    val const : Int => PunchGenerator = value => new ConstantPunchGenerator(value)
    val random : Int => PunchGenerator = upTo => new RandomPuchGenerator(upTo)
  }

  trait Shield{
    def absorb : PartialFunction[Int ,Int]
  }

  object Shield{


    val createShield : Int => Shield = energy => new ChargedShield(energy)
    val noShield : () => Shield = () => NoShield

    private class ChargedShield(initialEnergy:Int) extends Shield {
      private var currentEnergy = initialEnergy

      private val shieldBroken: PartialFunction[Int, Int] = ???

      private val absorbHit: PartialFunction[Int, Int] = ???

      override def absorb: PartialFunction[Int, Int] = absorbHit orElse shieldBroken
    }

    private object NoShield extends Shield{
      override def absorb:PartialFunction[Int,Int] = {
        case hit => hit
      }
    }

  }


  class Robot5 private(initialEnergy:Int,shield: Shield,punchGenerator: PunchGenerator){
    private var energy:Int=initialEnergy

    val isAlive : () =>  Boolean =() => energy > 0
    val energyStatus:() => Int = () => energy

    private val absorb : Int => Int = damage =>  shield.absorb(damage)
    private val reduceEnergy : Int =>Unit = damage => energy = Math.max(0, energy-damage)

    val takeHit : Int => Unit = absorb andThen reduceEnergy

    //between 0-25
    def punch : Int = punchGenerator.generatePunch()

  }

  object Robot5{

    def apply(initialEnergy:Int, shield:Shield,punchGenerator: PunchGenerator) ={
      require(initialEnergy>0, "initital energy must be greater than 0")
      new Robot5(initialEnergy,shield,punchGenerator)
    }

    val createDefault : Int => Robot5 = initialEnergy => new Robot5(initialEnergy,Shield.noShield(),PunchGenerator.const(10))
  }


  trait GameState
  object Continue extends GameState
  object End extends GameState

  object Game{

    val battle: (Robot5,Robot5) => Unit =  (r1,r2) => r2.takeHit(r1.punch)

    val gameState:PartialFunction[Robot5,GameState] = ???
  }

  object GameMessagesGenerator {
    val generateMessage:PartialFunction[GameState,String] = ???
  }

}


class Robot5Test extends FunSuite with MustMatchers {

  test("Game should be played tille the end"){
    val robot1=Robots5.Robot5(70,Robots5.Shield.createShield(15),PunchGenerator.const(10))
    val robot2=Robots5.Robot5(70,Robots5.Shield.createShield(10),PunchGenerator.const(30))


    Game.gameState(robot1) mustBe Continue
    Game.battle(robot2,robot1)
    Game.battle(robot2,robot1)
    Game.gameState(robot1) mustBe Continue
    Game.battle(robot2,robot1)
    Game.gameState(robot1) mustBe End

  }

  test("game message generator"){
    GameMessagesGenerator.generateMessage(Continue) mustBe "Continue Game"
    GameMessagesGenerator.generateMessage(End) mustBe "End Game!"
  }

  test("No shield is not protecting at all"){
    Robots5.Shield.noShield().absorb(10) mustBe 10
    Robots5.Shield.noShield().absorb(10) mustBe 10
    Robots5.Shield.noShield().absorb(10) mustBe 10
  }

  test("Standard shield should absorb hits"){
      val shield=Robots5.Shield.createShield(100)

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
    val robot = Robots5.Robot5(100,Robots5.Shield.noShield(),PunchGenerator.const(10))

      robot.punch mustBe 10
      robot.punch mustBe 10
      robot.punch mustBe 10
  }

  test("shield absorbs energy"){
    val robot=Robots5.Robot5(70,Robots5.Shield.createShield(10),PunchGenerator.const(10))
    robot.energyStatus() mustBe 70
    robot.takeHit(15)
    robot.energyStatus() mustBe 65
  }


  test("robots battle"){
    val robot1=Robots5.Robot5(70,Robots5.Shield.createShield(50),PunchGenerator.const(20))
    val robot2=Robots5.Robot5(70,Robots5.Shield.createShield(50),PunchGenerator.const(10))

    Robots5.Game.battle(robot1,robot2)
    robot2.energyStatus() mustBe 70

    Robots5.Game.battle(robot1,robot2)
    Robots5.Game.battle(robot1,robot2)
    robot2.energyStatus() mustBe 60
  }

  test("Robot with positive energy should be alive") {
    Robots5.Robot5.createDefault(20).isAlive() mustBe true
    Robots5.Robot5.createDefault(1).isAlive() mustBe true
    Robots5.Robot5.createDefault(200).isAlive() mustBe true
  }

  test("Robot initial energy should be greater than 0") {
    an[IllegalArgumentException] must be thrownBy Robots5.Robot5(0,Robots5.Shield.noShield(),PunchGenerator.const(10))
    an[IllegalArgumentException] must be thrownBy Robots5.Robot5(-1,Robots5.Shield.noShield(),PunchGenerator.const(10))
    an[IllegalArgumentException] must be thrownBy Robots5.Robot5(-100,Robots5.Shield.noShield(),PunchGenerator.const(10))
  }

  test("hit robot"){
    val robot=Robots5.Robot5.createDefault(100)

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
