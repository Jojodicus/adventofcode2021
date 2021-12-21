#include <iostream>
#include <fstream>
#include <string>
#include <memory>
#include <list>

namespace
{
    struct positions
    {
        std::uint_fast8_t p1;
        std::uint_fast8_t p2;
    };
    typedef struct positions Positions;

    std::uint_fast32_t puzzleOne(std::shared_ptr<Positions> p);
    // std::uint_fast32_t puzzleTwo(std::shared_ptr<Positions> p);
    std::shared_ptr<Positions> parseFile(std::string fileName);

    std::uint_fast32_t puzzleOne(std::shared_ptr<Positions> p)
    {
        // dice stats
        std::uint_fast16_t d100 = 1;
        std::uint_fast32_t rolls = 0;

        // positions
        std::uint_fast8_t p1_pos = p->p1;
        std::uint_fast8_t p2_pos = p->p2;

        // score
        std::uint_fast16_t p1_score = 0;
        std::uint_fast16_t p2_score = 0;

        // keep track of turn
        bool p1_turn = true;

        // current position/score
        std::uint_fast8_t *c_pos = &p1_pos;
        std::uint_fast16_t *c_score = &p1_score;

        // play
        while (p1_score < 1000 && p2_score < 1000)
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
                c_pos = &p1_pos;
                c_score = &p1_score;
            }
            else
            {
                c_pos = &p2_pos;
                c_score = &p2_score;
            }
        }

        // return score of losing player * number of rolls
        return *c_score * rolls;
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