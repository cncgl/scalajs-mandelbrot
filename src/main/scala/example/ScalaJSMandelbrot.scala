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

    // println(cols, ' ' + ratio + ' ' + repeat)

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
      -1
    }

    def countToColor(count: Int, base: Int): String = {
      if(count<0) return "black"

      var d: Int = count % base
      d *= 256/base

      val m: Int = (d/42.667).toInt
      var r = 0
      var g = 0
      var b = 0
      m match {
        case 0 => r = 0; g = 6*d; b = 255
        case 1 => r = 0; g = 255; b = 255-6*(d-43)
        case 2 => r = 6*(d-86); g = 255; b = 0
        case 3 => r = 255; g = 255 - 6*(d-129); b = 0
        case 4 => r = 255; g = 0; b = 6 * (d-171)
        case 5 => r = 255 - 6*(d-214); g = 0; b = 255
        case _ => r = 0; g = 0; b = 0
      }
      s"rgb($r, $g, $b)"
    }

    def run(): Unit = {
      clear()
      val step: Double = vs/SIZE
      for (x <- -SIZE/2 to SIZE/2) {
        val a = creal + step * x
        for (y <- -SIZE/2 to SIZE/2) {
          val b = cimag + step * y
          val n = mandelbrot(a, b)
          ctx.fillStyle = countToColor(n, cols)
          ctx.fillRect(SIZE/2 + x, SIZE/2 - y, 1, 1)
        }
      }
    }
  }
}
