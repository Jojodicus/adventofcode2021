#include <iostream>
#include <fstream>
#include <string>
#include <memory>
#include <list>

namespace
{
    struct pair
    {
        std::uint_fast32_t p1;
        std::uint_fast32_t p2;
    };
    typedef struct pair Positions;
    typedef struct pair Scores;
    typedef struct pair Wins;

    std::uint_fast32_t puzzleOne(std::shared_ptr<Positions> startpos);
    std::uint_fast32_t puzzleTwo(std::shared_ptr<Positions> startpos);
    std::shared_ptr<Positions> parseFile(std::string fileName);

    std::uint_fast32_t puzzleOne(std::shared_ptr<Positions> startpos)
    {
        // dice stats
        std::uint_fast16_t d100 = 1;
        std::uint_fast32_t rolls = 0;

        // positions
        Positions pos = {startpos->p1, startpos->p2};

        // score
        Scores score = {0, 0};

        // keep track of turn
        bool p1_turn = true;

        // current position/score
        std::uint_fast32_t *c_pos = &pos.p1;
        std::uint_fast32_t *c_score = &score.p1;

        // play
        while (score.p1 < 1000 && score.p2 < 1000)
        {
            // roll 3 times
            for (int i = 0; i < 3; i++)
            {
                *c_pos = (*c_pos - 1 + d100) % 10 + 1;

                d100 = d100 % 100 + 1;
            }
            rolls += 3;

            // update score
            *c_score += *c_pos;

            // switch turn
            p1_turn = !p1_turn;
            if (p1_turn)
            {
                c_pos = &pos.p1;
                c_score = &score.p1;
            }
            else
            {
                c_pos = &pos.p2;
                c_score = &score.p2;
            }
        }

        // return score of losing player * number of rolls
        return *c_score * rolls;
    }

    std::uint_fast32_t puzzleTwo(std::shared_ptr<Positions> startpos)
    {
        return 0;
    }

    std::shared_ptr<Positions> parseFile(std::string fileName)
    {
        // read input file
        std::ifstream file(fileName);
        std::string input((std::istreambuf_iterator<char>(file)),
                          std::istreambuf_iterator<char>());
        file.close();

        // parse input
        std::shared_ptr<Positions> p = std::make_shared<Positions>();
        std::size_t lb = input.find(':');
        p->p1 = input[lb + 2] - '0';
        lb = input.find(':', lb + 1);
        p->p2 = input[lb + 2] - '0';
        return p;
    }
}

int main(void)
{
    std::shared_ptr<Positions> pos = parseFile("input");
    std::cout << "Part 1: " << puzzleOne(pos) << std::endl;
    std::cout << "Part 2: " << pos->p2 << std::endl;
}