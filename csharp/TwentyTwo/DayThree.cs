using static AdventOfCode.Application;

namespace AdventOfCode.TwentyTwo;

class DayThree
{
    record class RucksackItems(string FirstHalf, string SecondHalf, string Combined);
    internal static void Solve()
    {
        int sum = 0, groupedSum = 0;
        string input = ReadInput("day3");
        List<RucksackItems> rucksackItems = new();
        List<List<RucksackItems>> groupedRucksackItems = new();

        ReadData(input, rucksackItems);

        foreach (var item in rucksackItems)
        {
            sum += CheckForCommonLetter(item.FirstHalf, item.SecondHalf);
        }

        for (int i = 0; i < rucksackItems.Count; i++)
        {
            if ((i + 1) % 3 == 0)
            {
                List<RucksackItems> grouped = new()
                {
                    rucksackItems[i - 2],
                    rucksackItems[i - 1],
                    rucksackItems[i],
                };
                groupedRucksackItems.Add(grouped);
            }
        }

        foreach (var groupedItems in groupedRucksackItems)
        {
            groupedSum += CheckForCommonLetter(groupedItems[0].Combined, groupedItems[1].Combined, groupedItems[2].Combined);
        }

        Console.WriteLine(" --- 2022 Day 3: Rucksack Reorganization ---\n");
        Console.WriteLine($"   The sum of all item type priorities of all the Elves' rucksack are: {sum}");
        Console.WriteLine($"   Also, all item type priorities of three grouped Elves' rucksack are: {groupedSum}\n");

        List<TwentyTwoTreeLineContent> dayThreeLine = new()
        {
            new TwentyTwoTreeLineContent(ConsoleColor.Gray, "@@@"),
            new TwentyTwoTreeLineContent(ConsoleColor.Green, "#"),
            new TwentyTwoTreeLineContent(ConsoleColor.Green, "@@"),
            new TwentyTwoTreeLineContent(ConsoleColor.Yellow, "@"),
            new TwentyTwoTreeLineContent(ConsoleColor.Green, "@@"),
            new TwentyTwoTreeLineContent(ConsoleColor.Yellow, "_/"),
            new TwentyTwoTreeLineContent(ConsoleColor.Gray, " ~   ~  "),
            new TwentyTwoTreeLineContent(ConsoleColor.Yellow, "\\ ' '. '.'."),
            new TwentyTwoTreeLineContent(ConsoleColor.Green, "@@"),
            new TwentyTwoTreeLineContent(ConsoleColor.Gray, "@@@#@@@@@@@@@@#@@"),
        };
        TwentyTwoTree.Add(dayThreeLine);
    }

    static void ReadData(string input, List<RucksackItems> rucksackItems)
    {
        string[] items = input.Trim().Split('\n');

        foreach (string item in items)
        {
            int middleLineIndex = item.Length / 2;
            string firstHalf = item[..middleLineIndex];
            string secondHalf = item[middleLineIndex..];
            rucksackItems.Add(new RucksackItems(firstHalf, secondHalf, item));
        }
    }

    static int CheckForCommonLetter(params string[] strings)
    {
        IEnumerable<char>[] charArrays = strings.Select(item => item.ToCharArray().ToHashSet()).ToArray();
        var commonLetter = charArrays.Aggregate((acc, set) => acc.Intersect(set));

        return commonLetter.First() switch
        {
            >= 'a' and <= 'z' => commonLetter.First() - 'a' + 1,
            >= 'A' and <= 'Z' => commonLetter.First() - 'A' + 27,
            _ => throw new ArgumentException($"Invalid character: {commonLetter.First()}")
        };
    }
}