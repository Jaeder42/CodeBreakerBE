package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.libs.Json

case class Target(target: String)
case class Result(result: String)

@Singleton
class GuessController @Inject() (val controllerComponents: ControllerComponents)
    extends BaseController {

  def index() = Action(parse.json) { request =>
    val message = request.body
    val guess = (message \ "guess").as[String]

    val targetTest = Target(target = "ff00ff")

    val targetArray = targetTest.target.split("")
    val guessArray = guess.split("")
    val resultArray = (1 to 6 map (_ => "0")).toArray
    guessArray.zipWithIndex.foreach { case (e, i) =>
      if (e.equals(targetArray(i))) {
        println(e, i)
        resultArray(i) = "X"
      } else if (
        targetArray
          .indexOf(e) > -1 && !e.equals(guessArray(targetArray.indexOf(e)))
      ) {
        resultArray(i) = "x"
      }
    }
    val result = Result(result = resultArray.mkString)
    Ok(Json.toJson(result).toString())
  }
}
