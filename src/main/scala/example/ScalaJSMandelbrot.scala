package example
import scala.scalajs.js.annotation.JSExport
import org.scalajs.dom
import org.scalajs.dom.html

@JSExport
object ScalaJSMandelbrot {
  @JSExport
  def main(canvas: html.Canvas): Unit = {
    val ctx = canvas.getContext("2d")
                    .asInstanceOf[dom.CanvasRenderingContext2D]

    val xmax = 400              // キャンバス Xmax
    val ymax = 400              // キャンバス Ymax
    val xcenter: Double = -0.75 // 中心座標 X
    val ycenter: Double = 0.0   // 中心座標 Y
    val vs: Double = 4.0        // 区画幅（直径）
    val repeat = 40             // 収束発散判定回数

    val xstart = xcenter - vs/2.0
    val ystart = ycenter - vs/2.0

    run()

    def clear(): Unit = {
      ctx.fillStyle = "black"
      ctx.fillRect(0, 0, xmax, ymax)
    }

    def mandelbrot(a: Double, b: Double): Int = {
      var x: Double = 0.0
      var y: Double = 0.0
      for(r <- 0 until repeat) {
        val x1 = x*x - y*y + a
        val y1 = 2.0 * x*y + b
        if (x1*x1 + y1*y1 > 4.0) return r
        x = x1
        y = y1
      }
      0
    }

    def run(): Unit = {
      clear()
      for (w <- 0 until xmax) {
        val a = xstart + vs / xmax * w
        for (h <- 0 until ymax) {
          val b = ystart + vs / ymax * h
          val n = mandelbrot(a, b)
          if (n > 0) {
            val r = n * 10
            val g = n * 10
            val b = n * 10
            ctx.fillStyle = s"rgb($r, $g, $b)"
            ctx.fillRect(w, h, 1, 1)
          }
        }
      }
    }
  }
}
