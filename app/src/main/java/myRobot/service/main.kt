package myRobot.service

import model.hands.brands.SamsungHand
import myRobot.models.hands.brands.SonyHand
import model.hands.brands.ToshibaHand
import myRobot.models.heads.brands.SamsungHead
import model.heads.brands.SonyHead
import myRobot.models.heads.brands.ToshibaHead
import model.legs.brands.SamsungLeg
import model.legs.brands.ToshibaLeg
import myRobot.models.MyRobot
import myRobot.models.legs.brands.SonyLeg

fun main(args: Array<String>) {

    // hands
    val sonyHand = SonyHand(100)
    val toshibaHand = ToshibaHand(90)
    val samsungHand = SamsungHand(150)

    // heads
    val sonyHead = SonyHead(200)
    val samsungHead = SamsungHead(300)
    val toshibaHead = ToshibaHead(150)

    // legs
    val sonyLeg = SonyLeg(80)
    val samsungLeg = SamsungLeg(100)
    val toshibaLeg = ToshibaLeg(60)


    val robot1 = MyRobot(sonyHead, samsungHand, toshibaLeg)
    val robot2 = MyRobot(samsungHead, samsungHand, toshibaLeg)
    val robot3 = MyRobot(toshibaHead, samsungHand, sonyLeg)


    println("---First robot---")
    robot1.action()
    println("---Second robot---")
    robot2.action()
    println("---Third robot---")
    robot3.action()


    val robots = arrayOf(robot1, robot2, robot3)


    val mostExpensiveRobot = Calculations.getMostExpensiveRobot(robots)
    println("---The most expensive robot---")
    mostExpensiveRobot.action()
    println("The most expensive robot costs: ${mostExpensiveRobot.getPrice()} $")
}

