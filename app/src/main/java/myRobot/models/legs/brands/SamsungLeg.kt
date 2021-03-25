package model.legs.brands

import model.legs.Leg

class SamsungLeg(price: Int) : Leg(price) {
    override fun makeStep() {
        println("Samsung's leg is making step")
    }
}