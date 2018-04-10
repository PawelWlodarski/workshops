package jug.lodz.workshops.starter.capstone.robots

import org.scalatest.{FunSuite, MustMatchers}

object Robots1 {

  class Robot1(initialEnergy:Int){
    require(???,s"initial energy must be larger than 0, actual $initialEnergy")

    def isAlive : Boolean = ???

    def takeHit(damage:Int):Unit = ???

    def energyStatus:Int = ???
  }
}

class Robot1Test extends FunSuite with MustMatchers {
  test("Robot with positive energy should be alive") {
    new Robots1.Robot1(20).isAlive mustBe true
    new Robots1.Robot1(1).isAlive mustBe true
    new Robots1.Robot1(200).isAlive mustBe true
  }

  test("Robot initial energy should be greater than 0") {
    an[IllegalArgumentException] must be thrownBy new Robots1.Robot1(0)
    an[IllegalArgumentException] must be thrownBy new Robots1.Robot1(-1)
    an[IllegalArgumentException] must be thrownBy new Robots1.Robot1(-100)
  }

  test("hit robot"){
    val robot=new Robots1.Robot1(100)

    robot.takeHit(30)
    val firstEnergystatus=robot.energyStatus
    robot.takeHit(45)
    val secondEnergystatus=robot.energyStatus
    robot.takeHit(100)
    val thirdEnergystatus=robot.energyStatus

    firstEnergystatus mustBe 70
    secondEnergystatus mustBe 25
    thirdEnergystatus mustBe 0

  }

}
