import model.Grid

val grid = Array.ofDim[Int](6,7)
grid(1)(1)=2
print(grid(1)(1))
print(grid(0)(0))

val test = new Grid
test.put(1,1)
print(test.toString)