package jug.lodz.workshops.starter.capstone.robots

import org.scalatest.{FunSuite, MustMatchers}


object Robots2 {

  class Robot2 private(initialEnergy:Int){
    private var energy:Int=initialEnergy

    def isAlive : Boolean = energy > 0

    def takeHit(damage:Int):Unit = energy = Math.max(0, energy-damage)

    def energyStatus:Int = energy

    def punch : Int = ???

  }

  object Robot2{

    private val forceFieldMultiplier = 2

    def apply(initialEnergy:Int, withForceField:Boolean=false):Robot2 = ???

    def battle(r1:Robot2,r2:Robot2): Unit = ???
  }

}

class Robot2Test extends FunSuite with MustMatchers {
  //EXERCISE2
  test("robot should generate punch between 0 and 50"){
    val robot = Robots2.Robot2(100,withForceField = true)

    (1 to 100).foreach{_=>
      robot.punch must be >=0
      robot.punch must be <=50
    }
  }

  test("force field multiplies energy"){
    val robot=Robots2.Robot2(70,withForceField = true)
    robot.energyStatus mustBe 140

    Robots2.Robot2(80,withForceField = true).energyStatus mustBe 160
    Robots2.Robot2(80).energyStatus mustBe 80
  }


  test("robots battle"){
    val robot1=Robots2.Robot2(70,withForceField = true)
    val robot2=Robots2.Robot2(100)

    Robots2.Robot2.battle(robot1,robot2)

    robot1.energyStatus mustBe 140
    robot2.energyStatus must be > 50
    robot2.energyStatus must be <= 100

  }

  //EXERCISE1
  test("Robot with positive energy should be alive") {
    Robots2.Robot2(20).isAlive mustBe true
    Robots2.Robot2(1).isAlive mustBe true
    Robots2.Robot2(200).isAlive mustBe true
  }

  test("Robot initial energy should be greater than 0") {
    an[IllegalArgumentException] must be thrownBy Robots2.Robot2(0)
    an[IllegalArgumentException] must be thrownBy Robots2.Robot2(-1)
    an[IllegalArgumentException] must be thrownBy Robots2.Robot2(-100)
  }

  test("hit robot"){
    val robot=Robots2.Robot2(100)

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
