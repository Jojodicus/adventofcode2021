#include <iostream>
#include <fstream>
#include <string>
#include <list>

namespace
{
    struct package
    {
        std::uint_fast64_t versions{0};
        std::uint_fast64_t values{0};
        std::string rest;
    };
    typedef struct package package;

    std::uint_fast64_t puzzleOne(package input);
    std::uint_fast64_t puzzleTwo(package input);
    package decode(std::string binary);
    std::string toBinary(std::string hex);
    std::uint_fast64_t toDecimal(std::string binary);

    std::uint_fast64_t puzzleOne(package input)
    {
        return input.versions;
    }

    std::uint_fast64_t puzzleTwo(package input)
    {
        return input.values;
    }

    package decode(std::string binary)
    {
        package result;

        result.versions = toDecimal(binary.substr(0, 3));
        int id = toDecimal(binary.substr(3, 3));

        if (id == 4) // literal
        {
            std::string cur = binary.substr(6);
            std::uint_fast32_t value{0};

            // parse
            while (cur.at(0) != '0')
            {
                value = (value << 4) | toDecimal(cur.substr(1, 4));
                cur = cur.substr(5);
            }
            value = (value << 4) | toDecimal(cur.substr(1, 4));
            cur = cur.substr(5);

            // fill and return
            result.values = value;
            result.rest = cur;
            return result;
        }

        // list of values in sub-packets
        std::list<std::uint_fast64_t> subValues;

        // operator
        if (binary.at(6) == '0') // fixed length
        {
            std::uint_fast32_t length = toDecimal(binary.substr(7, 15));
            std::string cur = binary.substr(22, length);
            result.rest = binary.substr(22 + length);

            // decode sub-packets
            while (cur.length() != 0)
            {
                package sub = decode(cur);
                subValues.push_back(sub.values);
                result.versions += sub.versions;
                cur = sub.rest;
            }
        }
        else // number of packets
        {
            std::uint_fast32_t n = toDecimal(binary.substr(7, 11));
            result.rest = binary.substr(18);

            // decode sub-packets
            for (std::uint_fast32_t i = 0; i < n; i++)
            {
                package sub = decode(result.rest);
                subValues.push_back(sub.values);
                result.versions += sub.versions;
                result.rest = sub.rest;
            }
        }

        // calculate expression (could be done with std::accumulate)
        std::uint_fast64_t value{subValues.front()};
        subValues.pop_front();
        for (std::uint_fast64_t v : subValues)
        {
            switch (id)
            {
            case 0:
                value += v;
                break;
            case 1:
                value *= v;
                break;
            case 2:
                value = std::min(value, v);
                break;
            case 3:
                value = std::max(value, v);
                break;
            case 5:
                if (value > v)
                    value = 1;
                else
                    value = 0;
                break;
            case 6:
                if (value < v)
                    value = 1;
                else
                    value = 0;
                break;
            case 7:
                if (value == v)
                    value = 1;
                else
                    value = 0;
                break;
            }
        }
        result.values = value;

        return result;
    }

    std::string toBinary(std::string hex)
    {
        std::string binary;
        for (char c : hex)
        {
            switch (c)
            {
            case '0':
                binary += "0000";
                break;
            case '1':
                binary += "0001";
                break;
            case '2':
                binary += "0010";
                break;
            case '3':
                binary += "0011";
                break;
            case '4':
                binary += "0100";
                break;
            case '5':
                binary += "0101";
                break;
            case '6':
                binary += "0110";
                break;
            case '7':
                binary += "0111";
                break;
            case '8':
                binary += "1000";
                break;
            case '9':
                binary += "1001";
                break;
            case 'a':
            case 'A':
                binary += "1010";
                break;
            case 'b':
            case 'B':
                binary += "1011";
                break;
            case 'c':
            case 'C':
                binary += "1100";
                break;
            case 'd':
            case 'D':
                binary += "1101";
                break;
            case 'e':
            case 'E':
                binary += "1110";
                break;
            case 'f':
            case 'F':
                binary += "1111";
                break;
            default:
                break;
            }
        }
        return binary;
    }

    std::uint_fast64_t toDecimal(std::string binary)
    {
        std::uint_fast64_t decimal{0};
        for (char c : binary)
        {
            decimal = (decimal << 1) | (c - '0');
        }
        return decimal;
    }
}

int main(void)
{
    // read input file
    std::ifstream file("input");
    std::string input((std::istreambuf_iterator<char>(file)),
                      std::istreambuf_iterator<char>());
    file.close();

    package decoded = decode(toBinary(input));

    std::cout << puzzleOne(decoded) << std::endl;
    std::cout << puzzleTwo(decoded) << std::endl;

    return 0;
}