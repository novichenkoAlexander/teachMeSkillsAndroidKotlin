package myRobot.models.heads.brands

import myRobot.models.heads.Head

class SamsungHead(price: Int) : Head(price) {
    override fun speak() {
        println("Samsung's head is speaking")
    }
}