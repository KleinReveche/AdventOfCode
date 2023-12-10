using System.ComponentModel;
using System.Reflection;

namespace AdventOfCode.Lib;

[AttributeUsage(AttributeTargets.Class)]
public class Problem(Year year, string day, string name) : Attribute
{
    public readonly string Day = day;
    public readonly string Name = name;
    public readonly Year Year = year;
}

[AttributeUsage(AttributeTargets.Class)]
public class WorkInProgress : Attribute;

public interface ISolution
{
    object PartOne();
    object PartTwo();
}

public enum Year
{
    TwentyTwo = 2022,
    TwentyThree = 2023
}

public static class SolutionRunner
{
    public static void RunSolution(ISolution solution)
    {
        var yearAttribute = solution.GetType().GetCustomAttribute<Problem>()?.Year.GetHashCode();
        var dayAttribute = solution.GetType().GetCustomAttribute<Problem>()?.Day.Replace("Day", "Day ");
        var nameAttribute = solution.GetType().GetCustomAttribute<Problem>()?.Name;
        Console.ForegroundColor = ConsoleColor.Red;
        Console.WriteLine(" --- {0} {1}: {2} ---\n", yearAttribute, dayAttribute, nameAttribute);
        Console.ForegroundColor = ConsoleColor.DarkGreen;
        Console.WriteLine(solution.PartOne());
        Console.WriteLine(solution.PartTwo());
        Console.ResetColor();
        Console.WriteLine();
    }

    public static ISolution[] GetSolutions(bool runAllSolutions, Year? year)
    {
        var solutionTypes = GetSolutionTypes();
        //sort by year, then day
        Array.Sort(solutionTypes, (x, y) =>
        {
            var xProblemAttribute = (Problem)Attribute.GetCustomAttribute(x, typeof(Problem))!;
            var yProblemAttribute = (Problem)Attribute.GetCustomAttribute(y, typeof(Problem))!;
            var yearComparison = xProblemAttribute.Year.CompareTo(yProblemAttribute.Year);
            return yearComparison != 0
                ? yearComparison
                : string.Compare(xProblemAttribute.Day, yProblemAttribute.Day, StringComparison.Ordinal);
        });

        if (runAllSolutions)
            return (from solutionType in solutionTypes
                where typeof(ISolution).IsAssignableFrom(solutionType)
                let problemAttribute = (Problem)Attribute.GetCustomAttribute(solutionType, typeof(Problem))!
                let inputData = ReadInput(problemAttribute.Year, problemAttribute.Day)
                select Activator.CreateInstance(solutionType, inputData) as ISolution
                into solution
                select solution!).ToArray();

        if (year is not null)
            solutionTypes = solutionTypes.Where(t =>
            {
                var problemAttribute = (Problem)Attribute.GetCustomAttribute(t, typeof(Problem))!;
                return problemAttribute.Year == year;
            }).ToArray();
        else
            throw new InvalidEnumArgumentException("Invalid Year.");

        return (from solutionType in solutionTypes
            where typeof(ISolution).IsAssignableFrom(solutionType)
            let problemAttribute = (Problem)Attribute.GetCustomAttribute(solutionType, typeof(Problem))!
            let inputData = ReadInput(problemAttribute.Year, problemAttribute.Day)
            select Activator.CreateInstance(solutionType, inputData) as ISolution
            into solution
            select solution!).ToArray();
    }

    public static ISolution GetSolution(Year year, string day)
    {
        var solutionTypes = GetSolutionTypes();
        var solutionType = solutionTypes.FirstOrDefault(t =>
        {
            var problemAttribute = (Problem)Attribute.GetCustomAttribute(t, typeof(Problem))!;
            return problemAttribute.Year == year && problemAttribute.Day == day;
        });
        var inputData = ReadInput(year, day);
        if (Activator.CreateInstance(solutionType!, inputData) is not ISolution solution)
            throw new Exception($"Could not create an instance of type {solutionType!.Name}.");
        return solution;
    }

    private static Type[] GetSolutionTypes()
    {
        var assembly = Assembly.GetExecutingAssembly();
        var types = assembly.GetTypes();
        var solutionTypes = types.Where(t => t.GetInterfaces().Contains(typeof(ISolution))).ToArray();
        return solutionTypes;
    }

    private static string ReadInput(Year year, string day)
    {
        var assembly = Assembly.GetExecutingAssembly(); // Get File from Assembly
        var name = $"{nameof(AdventOfCode)}.{Enum.GetName(typeof(Year), year)}.{day}.input.in";
        var stream = assembly.GetManifestResourceStream(name)!;
        StreamReader reader = new(stream);
        return reader.ReadToEnd().Trim().Replace("\r\n", "\n");
    }

    public static void RunWorkInProgressSolution()
    {
        var solutionTypes = GetSolutionTypes();
        var workInProgress = solutionTypes.FirstOrDefault(type =>
            type.GetCustomAttribute<WorkInProgress>() != null && type.GetInterfaces().Contains(typeof(ISolution)));
        if (workInProgress is null) return;
        var problemAttribute = (Problem)Attribute.GetCustomAttribute(workInProgress!, typeof(Problem))!;
        RunSolution(GetSolution(problemAttribute.Year, problemAttribute.Day));
    }
}