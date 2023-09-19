using System;
using System.Text;
using static AdventOfCode.Application;

namespace AdventOfCode.TwentyTwo;
internal static class DayFour
{
    internal static void Solve()
    {
        var input = ReadInput("day04");
        ReadDataAndCheck(input, out var inRangeCount, out var overlappedRangeCount);

        Console.WriteLine(" --- 2022 Day 4: Camp Cleanup ---\n");
        Console.WriteLine("   The Assignment Pairs that one range fully contain the other are: {0}", inRangeCount);
        Console.WriteLine("   The Assignment Pairs where the ranges overlap are: {0}\n", overlappedRangeCount);
    }

    private static void ReadDataAndCheck(string input, out int inRangeCount, out int overlappedRangeCount)
    {
        int inRange = 0, overlappedRange = 0;

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

                if (end1 < start2 || start1 > end2) continue;
                else overlappedRange++;
            }
            else break;
        }

        inRangeCount = inRange;
        overlappedRangeCount = overlappedRange;
    }
}