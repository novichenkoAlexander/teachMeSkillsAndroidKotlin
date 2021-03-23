package myRobot.models.heads

abstract class Head(private val price: Int) : IHead {
    override fun getPrice(): Int {
        return price
    }
}