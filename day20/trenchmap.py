image: set[tuple[int, int]] = set()
outsideLit: bool = False

enhancer: list[bool] = []


def puzzleOne() -> int:
    # enhance twice
    enhance()
    enhance()
    return lit()


def puzzleTwo() -> int:
    # already enhanced twice
    for _ in range(48):
        enhance()
    return lit()


def enhance():
    global image
    global outsideLit

    # boundry to not interfere with outsidelit
    minX = min(x for x, _ in image)
    maxX = max(x for x, _ in image)
    minY = min(y for _, y in image)
    maxY = max(y for _, y in image)

    newImage: set[tuple[int, int]] = set()

    for x in range(minX - 1, maxX + 2):
        for y in range(minY - 1, maxY + 2):
            index = 0

            # neighbours
            for dy in (-1, 0, 1):
                for dx in (-1, 0, 1):
                    index <<= 1

                    # lit in image
                    if (x + dx, y + dy) in image:
                        index |= 1
                    # lit in outside
                    elif outsideLit:
                        if x + dx < minX or x + dx > maxX or y + dy < minY or y + dy > maxY:
                            index |= 1

            # enhance
            if enhancer[index]:
                newImage.add((x, y))

    # update image
    image = newImage
    if outsideLit:
        outsideLit = enhancer[-1]
    else:
        outsideLit = enhancer[0]


def lit() -> int:
    return len(image)


def parseInput(input: str):
    global image
    global enhancer

    algorithm, imageData = input.split("\n\n")

    # enhancement algorithm
    enhancer = [True if c == "#" else False for c in algorithm]

    # image data
    imageData = [list(line) for line in imageData.split("\n")]
    for y, line in enumerate(imageData):
        for x, char in enumerate(line):
            if char == "#":
                image.add((x, y))


if __name__ == '__main__':
    with open("input", 'r') as f:
        parseInput(f.read())
    
    print("Part One:", puzzleOne())
    print("Part Two:", puzzleTwo())