package creditlimit.control

// required when using reflection, like `using` does
import scala.language.reflectiveCalls

object FileControl {
  def using[A <: {def close(): Unit}, B](resource: A)(f: A => B): B =
    try {
      f(resource)
    } finally {
      resource.close()
    }
}
