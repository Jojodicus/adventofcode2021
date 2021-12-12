edges: list[list[int]] = None
visited: set = set()
visitedTwice = None

# name -> indes and vice versa
n2i: dict[str, int] = {}
i2n: dict[int, str] = {}

def puzzleOne() -> int:
    return p1helper(n2i["start"])

def puzzleTwo() -> int:
    return p2helper(n2i["start"])

def p1helper(current: int) -> int:
    # final
    if i2n[current] == "end":
        return 1

    total = 0
    for i, v in enumerate(edges[current]):
        # extensions
        if v == 0 or i2n[i] == "start" or i in visited:
            continue

        # apply
        if i2n[i].islower():
            visited.add(i)

        # backtrack
        total += p1helper(i)

        # revert
        visited.discard(i)
    
    return total

def p2helper(current: int) -> int:
    global visitedTwice

    # final
    if i2n[current] == "end":
        #print([i2n[i] for i in trace])
        return 1

    # apply
    if i2n[current].islower():
        if current in visited:
            if visitedTwice != None:
                return 0
            visitedTwice = current
        visited.add(current)

    total = 0
    for i, v in enumerate(edges[current]):
        # extensions
        if v == 0 or i2n[i] == "start":
            continue

        # backtrack
        total += p2helper(i)
    
    # revert
    if visitedTwice == current:
        visitedTwice = None
    else:
        visited.discard(current)

    return total

def parseInput(input: str) -> None:
    lines = input.split('\n')
    connections = [line.split('-') for line in lines]
    
    # create a bidirectional dictionary of all the names
    counter = 0
    for connection in connections:
        for name in connection:
            if name not in n2i:
                n2i[name] = counter
                i2n[counter] = name
                counter += 1

    # create a numpy array of all the edges
    global edges
    edges = [[0 for _ in range(counter)] for _ in range(counter)]
    for connection in connections:
        edges[n2i[connection[0]]][n2i[connection[1]]] = 1
        edges[n2i[connection[1]]][n2i[connection[0]]] = 1

if __name__ == "__main__":
    with open("input", 'r') as f:
        parseInput(f.read())

    print(puzzleOne())
    print(puzzleTwo())
