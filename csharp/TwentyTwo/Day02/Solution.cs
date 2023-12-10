namespace AdventOfCode.TwentyTwo.Day02;

[Problem(Year.TwentyTwo, "Day02", "Rock Paper Scissors")]
public class Solution(string input) : ISolution
{
    public object PartOne()
    {
        var scores = Solve(false);
        return $"Your score is {scores}.";
    }

    public object PartTwo()
    {
        var altStrategyScore = Solve(true);
        return $"After the new instructions, your new score is {altStrategyScore}.";
    }

    private int Solve(bool isAlternateStrategyUsed)
    {
        var scores = 0;
        var altStrategyScore = 0;
        var movesList = ReadMoves();

        foreach (var moves in movesList)
        {
            scores += CheckScore(moves);
            altStrategyScore += AlternateStrategy(moves);
        }

        return isAlternateStrategyUsed ? altStrategyScore : scores;
    }

    private Moves[] ReadMoves()
    {
        var lines = input.Split("\n", StringSplitOptions.RemoveEmptyEntries);
        
        return lines.Select(line =>
        {
            var lineSplit = line.Split(' ');
            return new Moves(char.Parse(lineSplit[0]), char.Parse(lineSplit[1]));
        }).ToArray();
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
            _ => throw new Exception("Wrong Input, Check Data")
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

        return CheckScore(moves with { PlayerChoice = playerMove });
    }

    private record Moves(char OpponentChoice, char PlayerChoice);
}