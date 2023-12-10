namespace AdventOfCode.TwentyTwo.Day04;

[Problem(Year.TwentyTwo, "Day04", "Camp Cleanup")]
public class Solution(string input): ISolution
{
    public object PartOne()
    {
        var inRangeCount = Solve(input, false);
        return $"The Assignment Pairs that one range fully contain the other are: {inRangeCount}";
    }

    public object PartTwo()
    {
        var overlappedRangeCount = Solve(input, true);
        return $"The Assignment Pairs where the ranges overlap are: {overlappedRangeCount}";
    }

    private static int Solve(string input, bool isOverlapped)
    {
        var inRange = 0;
        var overlappedRange = 0;

        StringReader inputLines = new(input);
        while (true)
        {
            var line = inputLines.ReadLine();

            if (line != null)
            {
                var lineSplit = line.Split(',');
                var part1 = lineSplit[0].Split('-');
                var part2 = lineSplit[1].Split('-');

                var start1 = int.Parse(part1[0]);
                var end1 = int.Parse(part1[1]);
                var start2 = int.Parse(part2[0]);
                var end2 = int.Parse(part2[1]);

                if ((start1 >= start2) && (end1 <= end2) || ((start2 >= start1) && (end2 <= end1)))
                {
                    inRange++;
                }

                if (end1 >= start2 && start1 <= end2)
                    overlappedRange++;
            }
            else break;
        }

        return isOverlapped ? overlappedRange : inRange;
    }
}