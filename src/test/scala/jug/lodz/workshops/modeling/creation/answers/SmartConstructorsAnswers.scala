package jug.lodz.workshops.modeling.creation.answers

import org.apache.commons.codec.binary.Hex
import org.scalatest.{MustMatchers, WordSpecLike}

import scala.util.{Failure, Success, Try}

/**
  * Created by pawel on 19.04.17.
  */
class SmartConstructorsAnswers extends WordSpecLike with MustMatchers{
  //information transfer domain
  //Try represents offect of unfinished computation which ended exceptionally and
  //therefore can not be represented as a value
  "EXERCISE2" should {

    //here we will model hexadecimal type - used heavily in so called 'IoT'
    //we will create HexType which will represent values as "FFA00B10"
    //HexType can be created only through safe constructors
    "safely decode and serialize hex format" in {
      val input: String =255.toHexString  //explain method extension
      //        println(input) //check your self - ff

      HexType.decode(input) mustBe Success(HexType createFrom Array(255.toByte) )
      HexType.decode("MIREK") mustBe a[Failure[_]]
    }

  }
}


//recall relation beteen class and companion object
class HexType private(private val bytes:Array[Byte]){

  override def hashCode(): Int = bytes.hashCode
  override def equals(o2: scala.Any): Boolean =
    o2.isInstanceOf[HexType] && this.bytes.sameElements(o2.asInstanceOf[HexType].bytes)
  override def toString: String = bytes.mkString
}

object HexType{

  //smart constructor - more in level2
  def decode(input:String) : Try[HexType] = Try{
    val bytes=Hex.decodeHex(input.toCharArray)
    new HexType(bytes)
  }

  def serialize(h:HexType): String = Hex.encodeHex(h.bytes,false).mkString

  def createFrom(bytes: Array[Byte]) = new HexType(bytes)
}
