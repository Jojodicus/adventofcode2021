def puzzleOne(input) -> int:
    s: set[tuple[int, int, int]] = set()

    for cube in input:
        for x in range(cube[1], cube[2] + 1):
            if abs(x) > 50:
                break
            for y in range(cube[3], cube[4] + 1):
                if abs(y) > 50:
                    break
                for z in range(cube[5], cube[6] + 1):
                    if abs(z) > 50:
                        break

                    if cube[0]:
                        s.add((x, y, z))
                    else:
                        s.discard((x, y, z))

    return len(s)


def puzzleTwo(input) -> int:
    return 0


def parseLine(line: str) -> tuple[bool, int, int, int, int, int, int]:
    mode, position = line.split(' ')

    position = position.replace('x=', '').replace('y=', '').replace('z=', '')
    x, y, z = position.split(',')
    xmin, xmax = x.split('..')
    ymin, ymax = y.split('..')
    zmin, zmax = z.split('..')

    return (mode == 'on', int(xmin), int(xmax), int(ymin), int(ymax), int(zmin), int(zmax))


if __name__ == '__main__':
    with open('input', 'r') as f:
        lines = list(map(parseLine, f.readlines()))

    print(puzzleOne(lines))
