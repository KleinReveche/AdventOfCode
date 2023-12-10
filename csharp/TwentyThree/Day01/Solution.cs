using System.Text.RegularExpressions;

namespace AdventOfCode.TwentyThree.Day01;

[Problem(Year.TwentyThree, "Day01", "Trebuchet?!")]
public class Solution(string input) : ISolution
{
    private readonly string[] _lines = input.Split("\n");
    private static readonly Dictionary<string, int> Numbers = new()
    {
        { "one", 1 },
        { "two", 2 },
        { "three", 3 },
        { "four", 4 },
        { "five", 5 },
        { "six", 6 },
        { "seven", 7 },
        { "eight", 8 },
        { "nine", 9 }
    };

    public object PartOne()
    {
        const string regexPattern = @"1|2|3|4|5|6|7|8|9";
        var solution = Solve(_lines, regexPattern);

        return $"The sum of all of the calibration values is {solution}";
    }

    public object PartTwo()
    {
        const string regexPattern = @"|one|two|three|four|five|six|seven|eight|nine|1|2|3|4|5|6|7|8|9";
        var solution = Solve(_lines, regexPattern);

        return $"The sum of all of the calibration values with numbers spelt is {solution}";
    }

    private static int Solve(IEnumerable<string> input, string regexPattern)
    {
        var calibrationValues = (from line in input
            let first = Regex.Match(line, regexPattern)
            let last = Regex.Match(line, regexPattern, RegexOptions.RightToLeft)
            select $"{ConvertToInt(first.Value)}{ConvertToInt(last.Value)}"
            into firstLast
            select int.Parse(firstLast)).ToList();

        return calibrationValues.Sum();

        int ConvertToInt(string value) => Numbers.TryGetValue(value, out var number) ? number : int.Parse(value);
    }
}