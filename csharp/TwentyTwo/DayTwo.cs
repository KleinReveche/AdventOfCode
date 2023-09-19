using static AdventOfCode.Application;

namespace AdventOfCode.TwentyTwo;

internal static class DayTwo
{
    private record Moves(char OpponentChoice, char PlayerChoice);

    internal static void Solve()
    {
        var input = ReadInput("day02");
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
    }

    private static void ReadMoves(ICollection<Moves> movesList, TextReader inputLines)
    {
        while (true)
        {
            var line = inputLines.ReadLine();

            if (line != null)
            {
                var lineSplit = line.Split(' ');
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

    private static int CheckScore(Moves moves)
    {
        var playerChoice = moves.PlayerChoice;
        var opponentChoice = moves.OpponentChoice;

        var playerWinScore = playerChoice switch
        {
            'X' => opponentChoice switch
            {
                'C' => 6,
                'A' => 3,
                _ => 0
            },
            'Y' => opponentChoice switch
            {
                'A' => 6,
                'B' => 3,
                _ => 0
            },
            'Z' => opponentChoice switch
            {
                'B' => 6,
                'C' => 3,
                _ => 0
            },
            _ => throw new Exception("Wrong Input, Check Data")
        };

        var playScore = playerChoice switch
        {
            'X' => 1,
            'Y' => 2,
            'Z' => 3,
            _ => throw new Exception("Wrong Input, Check Data"),
        };

        return playerWinScore + playScore;
    }

    private static int AlternateStrategy(Moves moves)
    {
        var strategy = moves.PlayerChoice;
        var opponentChoice = moves.OpponentChoice;

        var playerMove = strategy switch
        {
            'X' => opponentChoice switch
            {
                'A' => 'Z',
                'B' => 'X',
                _ => 'Y'
            },
            'Y' => opponentChoice switch
            {
                'A' => 'X',
                'B' => 'Y',
                _ => 'Z'
            },
            'Z' => opponentChoice switch
            {
                'A' => 'Y',
                'B' => 'Z',
                _ => 'X'
            },
            _ => throw new Exception("Wrong Input, Check Data")
        };

        return CheckScore(new Moves(moves.OpponentChoice, playerMove));
    }
}