package example
import scala.scalajs.js.annotation.JSExport
import org.scalajs.dom
import org.scalajs.dom.html

@JSExport
object ScalaJSMandelbrot {
  @JSExport
  def main( limit: html.Select,
            colors: html.Select,
            zoom: html.Select,
            canvas: html.Canvas): Unit = {
    val ctx = canvas.getContext("2d")
                    .asInstanceOf[dom.CanvasRenderingContext2D]

    val SIZE = 400              // キャンバス サイズ
    val creal: Double = -0.75 // 中心座標 X
    val cimag: Double = 0.0   // 中心座標 Y
    val vs: Double = 4.0        // 区画幅（直径）

    val cols = colors.value.toInt
    val ratio = zoom.value.toInt
    val repeat = limit.value.toInt  // 収束発散判定回数

    println(cols, ' ' + ratio + ' ' + repeat)

    run()

    def clear(): Unit = {
      ctx.fillStyle = "black"
      ctx.fillRect(0, 0, SIZE, SIZE)
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

      val step: Double = vs/SIZE
      for (x <- -SIZE/2 to SIZE/2) {
        val a = creal + step * x
        for (y <- -SIZE/2 to SIZE/2) {
          val b = cimag + step * y
          val n = mandelbrot(a, b)
          if (n > 0) {
            val r = n * 10
            val g = n * 10
            val b = n * 10
            ctx.fillStyle = s"rgb($r, $g, $b)"
            ctx.fillRect(SIZE/2 + x, SIZE/2 - y, 1, 1)
          }
        }
      }
    }
  }
}
