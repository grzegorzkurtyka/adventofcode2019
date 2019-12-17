val f = {a:Int,b:Int ->
    a+b
}


val l = listOf<Int>(1,2,3,4,5,6)

l.foldRight(0, {z:Int,acc:Int->
    z+acc
    z+1
})
