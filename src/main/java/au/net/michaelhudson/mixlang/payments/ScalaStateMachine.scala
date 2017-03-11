package au.net.michaelhudson.mixlang.payments

import java.util.logging.Logger

/**
  * Created by mhudson
  *
  * @since 11/3/17.
  */
object ScalaStateMachine {
  val STATES = List("INIT", "CLEARED", "SETTLED", "REJECTED", "ABORTED")
  val EVENTS = List("CLEARED", "SETTLED", "REJECTED", "ABORTED")
  private val LOG = Logger.getLogger("JavaStateMachine")

  def calculate(currentState: String, event: String): String = {
    if (!STATES.contains(currentState)) {
      LOG.warning("BAD STATE")
      return "ERROR"
    }
    if (!EVENTS.contains(event)) {
      LOG.warning("BAD EVENT")
      return "ERROR"
    }

    (currentState, event) match {
      case ("INIT", e) => e;
      case ("CLEARED", e) => e;
      case ("SETTLED", "CLEARED") => "SETTLED";
      case ("SETTLED", "SETTLED") => "SETTLED";
      case ("ABORTED", "CLEARED") => "ABORTED";
      case ("ABORTED", "ABORTED") => "ABORTED";
      case ("REJECTED", "CLEARED") => "REJECTED";
      case ("REJECTED", "REJECTED") => "REJECTED";
      case _ =>
        LOG.warning(s"$currentState -/-> $event")
        "ERROR"
    }
  }
}
