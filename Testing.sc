
val grid = Array.ofDim[Int](6,7)
grid(1)(1)=2
print(grid(1)(1))
print(grid(0)(0))


var r:Int = 1
var c:Int = 3
while(r > 0){
  r-=1
  c-=1
}
println(r,c)