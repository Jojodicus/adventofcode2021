X = 0
Y = 0

local file = io.open("input", "r")
local input = file:read("*all")
input = input:gsub("forward", "X = X +")
input = input:gsub("down", "Y = Y +")
input = input:gsub("up", "Y = Y -")

load(input)()

print(X * Y)