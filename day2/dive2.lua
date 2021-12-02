X, Y = 0, 0

-- open input file
local input = io.open("input", "r"):read("*all")

-- replace directions with instructions
input = input:gsub("forward", "X = X +")
input = input:gsub("down", "Y = Y +")
input = input:gsub("up", "Y = Y -")

-- do instructions and print result
load(input)()
print(X * Y)