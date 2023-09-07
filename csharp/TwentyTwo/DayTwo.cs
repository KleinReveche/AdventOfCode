using static AdventOfCode.Application;

namespace AdventOfCode.TwentyTwo;

class DayTwo
{
    record class Moves(char OpponentChoice, char PlayerChoice);

    internal static void Solve()
    {
        string input = ReadInput("day02");
        int scores = 0, altStrategyScore = 0;
        StringReader inputLines = new(input);
        List<Moves> movesList = new(); 
        ReadMoves(movesList, inputLines);

        foreach (var moves in movesList)
        {
            scores += CheckScore(moves);
            altStrategyScore += AlternateStrategy(moves);
        }

        Console.WriteLine(" --- 2022 Day 2: Rock Paper Scissors ---\n");
        Console.WriteLine($"   Your score is {scores}.");
        Console.WriteLine($"   After the new instructions, your new score is {altStrategyScore}.\n");


        List<TwentyTwoTreeLineContent> dayTwoLine = new()
        {
            new TwentyTwoTreeLineContent(ConsoleColor.Yellow, "-~------'"),
            new TwentyTwoTreeLineContent(ConsoleColor.Gray, "    ~    ~ "),
            new TwentyTwoTreeLineContent(ConsoleColor.Yellow, "'--~-----~-~----___________--")
        };
        TwentyTwoTree.Add(dayTwoLine);
    }

    static void ReadMoves(List<Moves> movesList, StringReader inputLines)
    {
        while (true)
        {
            var line = inputLines.ReadLine();

            if (line != null)
            {
                string[] lineSplit = line.Split(' ');
                movesList.Add(
                    new Moves(
                        char.Parse(lineSplit[0]),
                        char.Parse(lineSplit[1])
                        )
                    );
            }
            else break;
        }
    }

    static int CheckScore(Moves moves)
    {
        var playerChoice = moves.PlayerChoice;
        var opponentChoice = moves.OpponentChoice;
        int playerWinScore;

        switch (playerChoice)
        {
            case 'X':
                if (opponentChoice == 'C') playerWinScore = 6;
                else if (opponentChoice == 'A') playerWinScore = 3;
                else playerWinScore = 0;
                break;
            case 'Y':
                if (opponentChoice == 'A') playerWinScore = 6;
                else if (opponentChoice == 'B') playerWinScore = 3;
                else playerWinScore = 0;
                break;
            case 'Z':
                if (opponentChoice == 'B') playerWinScore = 6;
                else if (opponentChoice == 'C') playerWinScore = 3; 
                else playerWinScore = 0;
                break;
            default:
                throw new Exception("Wrong Input, Check Data");
        }

        int playScore = playerChoice switch
        {
            'X' => 1,
            'Y' => 2,
            'Z' => 3,
            _ => throw new Exception("Wrong Input, Check Data"),
        };

        return playerWinScore + playScore;
    }

    static int AlternateStrategy(Moves moves)
    {
        var strategy = moves.PlayerChoice;
        var opponentChoice = moves.OpponentChoice;
        char playerMove;

        switch (strategy)
        {
            case 'X':
                if (opponentChoice == 'A') playerMove = 'Z';
                else if (opponentChoice == 'B') playerMove = 'X';
                else playerMove = 'Y';
                break;
            case 'Y':
                if (opponentChoice == 'A') playerMove = 'X';
                else if (opponentChoice == 'B') playerMove = 'Y';
                else playerMove = 'Z';
                break;
            case 'Z':
                if (opponentChoice == 'A') playerMove = 'Y';
                else if (opponentChoice == 'B') playerMove = 'Z'; 
                else playerMove = 'X';
                break;
            default:
                throw new Exception("Wrong Input, Check Data");
        }

        return CheckScore(new Moves(moves.OpponentChoice, playerMove));
    }
}