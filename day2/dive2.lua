X, Y, Aim = 0, 0, 0

-- open input file
local input = io.open("input", "r"):read("*all")

-- replace directions with instructions
input = input:gsub("down", "Aim = Aim +")
input = input:gsub("up", "Aim = Aim -")
input = input:gsub("forward %d", "X = X + %0; Y = Y + Aim * %0"):gsub("forward", "")

-- do instructions and print result
load(input)()
print(X * Y)