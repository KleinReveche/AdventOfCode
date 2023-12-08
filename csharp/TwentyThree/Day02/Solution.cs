using System.Text.RegularExpressions;
using AdventOfCode.Lib;

namespace AdventOfCode.TwentyThree.Day02;

[Problem(Year.TwentyThree, "Day02", "Cube Conundrum")]
public class Solution(string input) : ISolution
{
    public object PartOne()
    {
        var games = input.Split("\n", StringSplitOptions.RemoveEmptyEntries);
        var sum = games.Select(ParseGame).Where(game => game is { Red: <= 12, Green: <= 13, Blue: <= 14 })
            .Select(id => id.Id).Sum();
        return $"The sum of the IDs of those games is {sum}.";
    }

    public object PartTwo()
    {
        var games = input.Split("\n", StringSplitOptions.RemoveEmptyEntries);
        var sum = games.Select(ParseGame).Select(game => game.Red * game.Green * game.Blue).Sum();
        return $"The sum of the power of these sets is {sum}.";
    }

    private static Game ParseGame(string line)
    {
        var id = ParseLine(line, @"Game (\d+): ").First();
        var red = ParseLine(line, @"(\d+) red").Max();
        var green = ParseLine(line, @"(\d+) green").Max();
        var blue = ParseLine(line, @"(\d+) blue").Max();
        return new Game(id, red, green, blue);
    }

    private static List<int> ParseLine(string line, string regex)
    {
        var matches = Regex.Matches(line, regex);
        var numbers = new List<int>();
        foreach (Match match in matches) numbers.Add(int.Parse(match.Groups[1].Value));
        return numbers;
    }

    private record Game(int Id, int Red, int Green, int Blue);
}