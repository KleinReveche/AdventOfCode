using System.Text.RegularExpressions;

namespace AdventOfCode.TwentyTwo.Day04;

[Problem(Year.TwentyTwo, "Day04", "Camp Cleanup")]
public partial class Solution(string input): ISolution
{
    public object PartOne()
    {
        var inRangeCount = Solve(false);
        return $"The Assignment Pairs that one range fully contain the other are: {inRangeCount}";
    }

    public object PartTwo()
    {
        var overlappedRangeCount = Solve(true);
        return $"The Assignment Pairs where the ranges overlap are: {overlappedRangeCount}";
    }

    private int Solve(bool isOverlapped)
    {
        var inRange = 0;
        var overlappedRange = 0;
        var lines = input.Split("\n", StringSplitOptions.RemoveEmptyEntries);
        var regex = PairsRegex();
        
        foreach (var line in lines)
        {
            var match = regex.Match(line).Groups;
            var start1 = int.Parse(match[1].Value);
            var end1 = int.Parse(match[2].Value);
            var start2 = int.Parse(match[3].Value);
            var end2 = int.Parse(match[4].Value);

            if (start1 >= start2 && end1 <= end2 || (start2 >= start1 && end2 <= end1)) inRange++;
            if (end1 >= start2 && start1 <= end2) overlappedRange++;
        }

        return isOverlapped ? overlappedRange : inRange;
    }

    [GeneratedRegex(@"(\d+)-(\d+),(\d+)-(\d+)")]
    private static partial Regex PairsRegex();
}