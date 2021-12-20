image: set[tuple[int, int]] = set()
outsideLit: bool = False

enhancer: list[bool] = []


def puzzleOne() -> int:
    pass


def puzzleTwo() -> int:
    pass


def lit() -> int:
    return len(image)


def parseInput(input: str):
    global image
    global enhancer

    algorithm, imageData = input.split("\n\n")

    # enhancement algorithm
    algorithm = algorithm.replace("\n", "")
    enhancer = [True if c == "#" else False for c in algorithm]

    # image data
    imageData = [list(line) for line in imageData.split("\n")]
    for y, line in enumerate(imageData):
        for x, char in enumerate(y):
            if char == "#":
                image.add((x, y))


if __name__ == '__main__':
    with open("input", 'r') as f:
        parseInput(f.read())
    
