import model.{Grid, Player}

var grid = new Grid

for (i <- 1 to 4) grid.put(0, 1)
for (i <- 1 to 3) grid.put(3, 1)

println(grid.toString)
grid.checkConnect4(grid.put(3, 1),1)

println(grid.toString)

