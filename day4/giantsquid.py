from textwrap import wrap

drawn: list[int] = list()

# filter numbers already drawn
def filterMarked(l: list) -> list:
    return list(filter(lambda n: n not in drawn, l))

class Bingo:
    def __init__(self, board: str) -> None:
        # convert input string to 2d board
        self.board: list[list[int]] = [list(int(n) for n in wrap(row, 2)) for row in board.split('\n')]
        # for last element
        if len(self.board) > len(self.board[0]):
            self.board.pop()

    def done(self) -> bool:
        # any row is done
        for row in self.board:
            if len(filterMarked(row)) == 0:
                return True

        # and column is done
        for col in list(map(list, zip(*self.board))):
            if len(filterMarked(col)) == 0:
                return True
        
        # no finished line
        return False

    def score(self, last: int) -> int:
        # last drawn number * sum of all unmarked numbers on board
        return last * sum(sum(filterMarked(row)) for row in self.board)

def puzzleOne() -> int:
    # loop over draws
    for d in draws:
        # add draw to game
        drawn.append(int(d))
        # check for won game
        for g in games:
            if g.done():
                return g.score(d)

def puzzleTwo() -> int:
    # consider every number drawn
    global drawn
    drawn = draws
    losers: list[Bingo] = list()
    # as long as every board still won
    while len(losers) == 0:
        # remove last draw
        last = drawn.pop()
        # check for loser
        losers = list(filter(lambda g: not g.done(), games))
    
    # return final score of last winning board
    drawn.append(last)
    return losers[0].score(last)

if __name__ == "__main__":
    # read input file
    with open("input", 'r') as file:
        draws: list[int] = [int(n) for n in file.readline().split(',')]
        file.readline()
        boards: list[str] = file.read().split("\n\n")

    # initialize boards
    games: list[Bingo] = list()
    for b in boards:
        games.append(Bingo(b))
    
    # execute puzzles
    print(puzzleOne())
    print(puzzleTwo())
