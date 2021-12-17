xMin: int
xMax: int
yMin: int
yMax: int


def puzzleOne() -> int:
    # maximum velocity -> -yMin
    # use gauss to get height of throw
    return (yMin) * (yMin + 1) // 2


def puzzleTwo() -> int:
    count = 0

    # consider throws that could pass through the box
    # could land within y bound
    for vy in range(yMin, -yMin + 1):
        # definitely don't overshoot the box immediately
        for vx in range(0, xMax + 1):
            if (throwPasses(0, 0, vx, vy)):
                count += 1

    return count


def step(x: int, y: int, vx: int, vy: int) -> tuple[int, int, int, int]:
    # decrease vx with drag
    if (vx > 0):
        newVx = vx - 1
    elif (vx < 0):
        newVx = vx + 1
    else:
        newVx = 0

    # increase y with gravity and step
    return x + vx, y + vy, newVx, vy - 1


def inBox(x: int, y: int) -> bool:
    # check if x and y are within the box
    return xMin <= x <= xMax and yMin <= y <= yMax


def throwPasses(x: int, y: int, vx: int, vy: int) -> bool:
    # check if the throw will pass through the box
    while x <= xMax and y >= yMin:
        if (inBox(x, y)):
            return True

        x, y, vx, vy = step(x, y, vx, vy)

    # overshot
    return False


if __name__ == "__main__":
    # open
    with open("input", "r") as f:
        input = f.read()[12:].strip()

    # parse
    input = input.split(",")
    inXs = input[0].split("=")[1].strip().split("..")
    inYs = input[1].split("=")[1].strip().split("..")
    xMin, xMax = int(inXs[0]), int(inXs[1])
    yMin, yMax = int(inYs[0]), int(inYs[1])

    print(puzzleOne())
    print(puzzleTwo())
