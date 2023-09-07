using System;
using System.Text;
using static AdventOfCode.Application;

namespace AdventOfCode.TwentyTwo;
class DayFour
{
    record class SectionIdRange(Range FirstRange, Range SecondRange);

    internal static void Solve()
    {
        string input = ReadInput("day04");
        ReadDataAndCheck(input, out int inRangeCount, out int overlappedRangeCount);

        Console.WriteLine(" --- 2022 Day 4: Camp Cleanup ---\n");
        Console.WriteLine("   The Assignment Pairs that one range fully contain the other are: {0}", inRangeCount);
        Console.WriteLine("   The Assignment Pairs where the ranges overlap are: {0}", overlappedRangeCount);

        List<TwentyTwoTreeLineContent> dayThreeLine = new()
        {
            new TwentyTwoTreeLineContent(ConsoleColor.Gray, "@@@#@@"),
            new TwentyTwoTreeLineContent(ConsoleColor.Green, "##"),
            new TwentyTwoTreeLineContent(ConsoleColor.Yellow, "@"),
            new TwentyTwoTreeLineContent(ConsoleColor.Green, "@@"),
            new TwentyTwoTreeLineContent(ConsoleColor.Yellow, ".'"),
            new TwentyTwoTreeLineContent(ConsoleColor.Gray, " ~  "),
            new TwentyTwoTreeLineContent(ConsoleColor.Yellow, "'."),
            new TwentyTwoTreeLineContent(ConsoleColor.White, "/\\"),
            new TwentyTwoTreeLineContent(ConsoleColor.Yellow, "'."),
            new TwentyTwoTreeLineContent(ConsoleColor.White, "/\\"),
            new TwentyTwoTreeLineContent(ConsoleColor.Yellow, "' ."),
            new TwentyTwoTreeLineContent(ConsoleColor.Green, "@#@"),
            new TwentyTwoTreeLineContent(ConsoleColor.Gray, "@@@@@@@@@#@@@@#@@@"),
        };
        TwentyTwoTree.Add(dayThreeLine);
    }

    static void ReadDataAndCheck(string input, out int inRangeCount, out int overlappedRangeCount)
    {
        int inRange = 0, overlappedRange = 0;

        StringReader inputLines = new(input);
        while (true)
        {
            var line = inputLines.ReadLine();

            if (line != null)
            {
                string[] lineSplit = line.Split(',');
                string[] part1 = lineSplit[0].Split('-');
                string[] part2 = lineSplit[1].Split('-');

                int start1 = int.Parse(part1[0]);
                int end1 = int.Parse(part1[1]);
                int start2 = int.Parse(part2[0]);
                int end2 = int.Parse(part2[1]);

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