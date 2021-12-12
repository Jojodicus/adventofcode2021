from bidict import bidict
import numpy as np

names: bidict = bidict()
edges: list(list()) = None
visited: set = set()
visitedTwice = None

n2i: dict() = {}
i2n: dict() = {}

def puzzleOne() -> int:
    global visited
    visited = [0] * len(n2i)
    return p1helper(n2i["start"])

def puzzleTwo() -> int:
    global visited
    visited = [0] * len(n2i)
    return p2helper(n2i["start"])

def p1helper(current: int) -> int:
    # final
    if i2n[current] == "end":
        return 1

    total = 0
    for i, v in enumerate(edges[current]):
        # extensions
        if v == 0 or i2n[i] == "start" or visited[i] == 1:
            continue

        # apply
        if i2n[i].islower():
            visited[i] = 1

        # backtrack
        total += p1helper(i)

        # revert
        if i2n[i].islower():
            visited[i] = 0
    
    return total

def p2helper(current: int) -> int:
    global visitedTwice

    if i2n[current] == "end":
        #print([i2n[i] for i in trace])
        return 1

    if visited[current] == 1:
        if visitedTwice != None:
            return 0
        visitedTwice = current

    if i2n[current].islower():
        visited[current] = 1

    total = 0
    for i, v in enumerate(edges[current]):
        # extensions
        if v == 0 or i2n[i] == "start":
            continue

        # backtrack
        total += p2helper(i)
    
    if visitedTwice == current:
        visitedTwice = None
    else:
        visited[current] = 0

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
