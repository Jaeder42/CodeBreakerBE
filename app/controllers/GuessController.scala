package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.libs.Json

case class Target(target: String)
case class Guess(guess: String)
case class Result(result: String)

@Singleton
class GuessController @Inject() (val controllerComponents: ControllerComponents)
    extends BaseController {

  def index() = Action(parse.json) { request =>
    val message = request.body
    val requestGuess = (message \ "guess").as[String]
    val guess = Guess(guess = requestGuess)

    val targetTest = Target(target = "ff00ff")

    val targetArray = targetTest.target.split("")
    val guessArray = guess.guess.split("")
    val resultArray = (1 to 6 map(_ => "0")).toArray
    guessArray.zipWithIndex.foreach{case (e, i) =>
      if(e.equals(targetArray(i)))  {
        println(e, i)
        resultArray(i) = "X"
      } else if (targetArray.indexOf(e) > -1) {
        // resultArray(i) = "x"
      }
    }
    val result = Result(result=resultArray.mkString)
    Ok(Json.toJson(result).toString())
  }
}
