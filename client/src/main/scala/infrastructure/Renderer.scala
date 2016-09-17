package infrastructure

import domain._
import org.scalajs.dom

trait Renderer[Context] {
  def render(ctx: Context, world: GameWorld)
}

object CanvasRenderer extends Renderer[dom.CanvasRenderingContext2D] {
  type canvasCtx = dom.CanvasRenderingContext2D
  override def render(ctx: canvasCtx, world: GameWorld): Unit = {
    ctx.fillStyle = "black"
    ctx.fillRect(0, 0, ctx.canvas.width, ctx.canvas.height)

    val snacks = world.snacks
    val snakes = world.snakes

    snakes.foreach(renderSnake(ctx, _))
    snacks.foreach(renderSnack(ctx, _))
  }

  def drawPoint(ctx: canvasCtx, position: Position, scalingFactor: Int = 10): Unit = {
    ctx.fillRect(position.x * scalingFactor, position.y * scalingFactor,scalingFactor, scalingFactor)
  }

  def renderSnake(ctx: canvasCtx, snake: Snake): Unit = {
    ctx.fillStyle = "red"
    val snakeHead = snake.body.head
    drawPoint(ctx, snakeHead)

    ctx.fillStyle = "green"
    snake.body.tail.foreach(drawPoint(ctx, _))
  }

  def renderSnack(ctx: canvasCtx, snacks: Snacks): Unit = {
    snacks match {
      case Chocolate(p) =>
        ctx.fillStyle = "orange"
        drawPoint(ctx, p)
    }
  }
}