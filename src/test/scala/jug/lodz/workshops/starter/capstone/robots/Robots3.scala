package jug.lodz.workshops.starter.capstone.robots

import jug.lodz.workshops.starter.capstone.robots.Robots3.PunchGenerator
import org.scalatest.{FunSuite, MustMatchers}

object Robots3 {

  trait PunchGenerator{
    def generatePunch():Int
  }


  object PunchGenerator{
    def const(value:Int):PunchGenerator = ???
    def random(upTo:Int):PunchGenerator = ???
  }

  trait Shield{
    def absorb(hit:Int):Int
  }

  object Shield{

    def createShield(energy:Int):Shield = ???

    def noShield():Shield= ???
  }


  class Robot3 private(initialEnergy:Int,shield: Shield,punchGenerator: PunchGenerator){
    private var energy:Int=initialEnergy

    def isAlive : Boolean = energy > 0

    def takeHit(damage:Int):Unit = {
      val damageLeft=shield.absorb(damage)
      energy = Math.max(0, energy-damageLeft)
    }

    def energyStatus:Int = energy

    def punch : Int = punchGenerator.generatePunch()

  }

  object Robot3{

    def apply(initialEnergy:Int, shield:Shield,punchGenerator: PunchGenerator) ={
      require(initialEnergy>0, "initital energy must be greater than 0")
      new Robot3(initialEnergy,shield,punchGenerator)
    }

    def createDefault(initialEnergy:Int) = new Robot3(initialEnergy,Shield.noShield(),PunchGenerator.const(10))


    def battle(r1:Robot3,r2:Robot3): Unit = r2.takeHit(r1.punch)
  }

}


class Robot3Test extends FunSuite with MustMatchers {
  test("No shield is not protecting at all"){
    Robots3.Shield.noShield().absorb(10) mustBe 10
    Robots3.Shield.noShield().absorb(10) mustBe 10
    Robots3.Shield.noShield().absorb(10) mustBe 10
  }

  test("Standard shield should absorb hits"){
      val shield=Robots3.Shield.createShield(100)

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
    val robot = Robots3.Robot3(100,Robots3.Shield.noShield(),PunchGenerator.const(10))

      robot.punch mustBe 10
      robot.punch mustBe 10
      robot.punch mustBe 10
  }

  test("shield absorbs energy"){
    val robot=Robots3.Robot3(70,Robots3.Shield.createShield(10),PunchGenerator.const(10))
    robot.energyStatus mustBe 70
    robot.takeHit(15)
    robot.energyStatus mustBe 65
  }


  test("robots battle"){
    val robot1=Robots3.Robot3(70,Robots3.Shield.createShield(50),PunchGenerator.const(20))
    val robot2=Robots3.Robot3(70,Robots3.Shield.createShield(50),PunchGenerator.const(10))

    Robots3.Robot3.battle(robot1,robot2)
    robot2.energyStatus mustBe 70

    Robots3.Robot3.battle(robot1,robot2)
    Robots3.Robot3.battle(robot1,robot2)
    robot2.energyStatus mustBe 60
  }

  test("Robot with positive energy should be alive") {
    Robots3.Robot3.createDefault(20).isAlive mustBe true
    Robots3.Robot3.createDefault(1).isAlive mustBe true
    Robots3.Robot3.createDefault(200).isAlive mustBe true
  }

  test("Robot initial energy should be greater than 0") {
    an[IllegalArgumentException] must be thrownBy Robots3.Robot3(0,Robots3.Shield.noShield(),PunchGenerator.const(10))
    an[IllegalArgumentException] must be thrownBy Robots3.Robot3(-1,Robots3.Shield.noShield(),PunchGenerator.const(10))
    an[IllegalArgumentException] must be thrownBy Robots3.Robot3(-100,Robots3.Shield.noShield(),PunchGenerator.const(10))
  }

  test("hit robot"){
    val robot=Robots3.Robot3.createDefault(100)

    robot.takeHit(30)
    val firstEnergyStatus=robot.energyStatus
    robot.takeHit(45)
    val secondEnergyStatus=robot.energyStatus
    robot.takeHit(100)
    val thirdEnergyStatus=robot.energyStatus

    firstEnergyStatus mustBe 70
    secondEnergyStatus mustBe 25
    thirdEnergyStatus mustBe 0

  }

}
