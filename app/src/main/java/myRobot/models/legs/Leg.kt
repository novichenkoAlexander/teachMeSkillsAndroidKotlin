package model.legs

abstract class Leg(private val price: Int) : ILeg {
    override fun getPrice(): Int {
        return price
    }

}