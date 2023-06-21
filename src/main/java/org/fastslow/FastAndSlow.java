package org.fastslow;//using System;
//        using System.Collections;
//        using System.Collections.Generic;
//        using System.Diagnostics;
//        using System.Linq;
//        using System.Numerics;
//        using System.Text;
//
//        namespace Test
//        {
///*
//Optimize "fast" Method to act the same as "slow" method but for less than 50 ms
//Algorithm and what need method to do is explained in comment at the end.
//*/
//class Program
//{
//    static void Main(string[] args)
//    {
//        var rand = new Random();
//        int numN = 50000;
//
//        var list = new List<int>(numN);
//
//        var sb = new StringBuilder(); //"1 2 1 2 1 2 4 2 6 3 2 1 4"
//        for (int i = 0; i < numN; i++)
//        {
//            sb.Append($"{rand.Next(numN)} ");
//        }
//
//        string inputString = sb.ToString();
//        Console.WriteLine("Input: " + inputString.Substring(0,50));
//
//        var watch = new System.Diagnostics.Stopwatch();
//
//        List<int> resultSlow;
//        List<int> resultFast;
//
//
//        resultSlow = Program.slow(inputString);
//        watch.Start();
//        resultFast = Program.fast(inputString);
//        watch.Stop();
//
//        if (!Enumerable.SequenceEqual(resultSlow, resultFast))
//        {
//            Console.WriteLine("Error!!! Not Equal results!");
//        }
//
//        Console.WriteLine("Max Jumps: " + resultSlow.Max());
//        Console.WriteLine("Max Jumps fast: " + resultFast.Max());
//        Console.WriteLine("Jumps: " + string.Join(" ", resultSlow.Take(20)));
//
//        Console.WriteLine($"Execution Time: {watch.ElapsedMilliseconds} ms");
//    }

//    static List<int> fast(String inputNumbers)
//    {
//        var numbers = inputNumbers.Trim().Split().Select(int.Parse).ToArray();
//        int numN = numbers.Count();
//        int initialJump = 0;
//        int next = 0;
//        int lastValue;
//
//        var list = new List<int>(numN);
//
//        int counter, max = numbers.Max();
//        Dictionary<int,int> map = new Dictionary<int,int>();
//        //var uniqueList = new List<int>(numN);
//        HashSet<int> set = new HashSet<int>();
//
//        for (int i = 0; i < numN; i++)
//        {
//            initialJump = numbers[i];
//            counter = 0;
//
//            //Check if the given number have already jump value
//            if(!map.ContainsKey(i)) {
//                if(initialJump == max) {
//                    map[i] = 0;
//                    continue;
//                }
//                lastValue = i;
//                int addValue = 0;
//
//                //uniqueList.Clear();
//                //uniqueList.Add(lastValue);
//
//                set.Clear();
//                set.Add(lastValue);
//
//                for (int j = i + 1; j < numN; j++){
//                    next = numbers[j];
//                    if (initialJump < next){
//                        counter++;
//                        if(map.ContainsKey(j)){
//                            addValue = map[j];
//                            break;
//                        } else {
//                            initialJump = next;
//                            //if(!uniqueList.Contains(j)) {
//                            //	uniqueList.Add(j);
//                            //}
//                            set.Add(j);
//                        }
//                    }
//                }
//
//
//                // Store Jump value into Map
//                foreach(int s in set) {
//                    map[s] = counter + addValue;
//                    counter--;
//                }
//            }
//        }
//
//
//        var sortedMap = map.OrderBy(kv => kv.Key);
//        list = sortedMap.Select(kv => kv.Value).ToList();
//        return list;
//
//    }



//    static List<int> slow(String inputNumbers)
//    {
//        var numbers = inputNumbers.Trim().Split().Select(int.Parse).ToArray();
//        int numN = numbers.Count();
//
//
//        int initialJump = 0;
//        int next = 0;
//
//        var list = new List<int>(numN);
//
//
//
//        int counter = 0, max = numbers.Max();
//        for (int i = 0; i < numN; i++)
//        {
//            initialJump = numbers[i];
//            // partial optimization
//            if (initialJump == max)
//            {
//                list.Add(0);
//                continue;
//            }
//            for (int j = i + 1; j < numN; j++)
//            {
//                next = numbers[j];
//                if (initialJump < next)
//                {
//                    counter++;
//                    initialJump = next;
//                }
//            }
//            list.Add(counter);
//            counter = 0;
//        }
//
//        return list;
//
//    }

        /*
         Jumps

Given a sequence of elements(numbers), calculate the longest possible sequence of 'jumps' from each number.

Each 'jump' must be made according to the following rules:

    You can only 'jump' on a number that is greater than the current one;
    You can 'jump' on a number, only if there isn't one with a greater value between;
    You can 'jump' only from left to right;

Input

Read from the standard input

    On the first line, you will find the number N
        The number of elements
    On the second line you will find N numbers, separated by a space
        The elements themselves

The input will be correct and in the described format, so there is no need to check it explicitly.
Output

Print to the standard output

    On the first line, print the length of the longest sequence of jumps
    On the second line, print the lengths of the sequences, starting from each element

Constraints

    The N will always be less than 50000

Sample Tests
Input

1 4 2 6 3 4

Output

2 1 1 0 1 0

Explanation

    Element 1:
        1 -> 4 -> 6 (2 jumps)
    Element 2:
        4 -> 6 (1 jump)
    Element 3:
        2 -> 6 (1 jump)
    Element 4:
        6 (0 jumps)
    Element 5:
        3 -> 4 (1 jump)
    Element 6:
        4 -> (0 jumps)

Input

1 1 1 1 1

Output

0 0 0 0 0
         * */


//}
//}


//static List<int>fast(string inputNumbers)
//        {
//
//        var numbers=inputNumbers.Trim().Split().Select(int.Parse).ToArray();
//        int numN=numbers.Count(),initialJump=0,next=0;
//
//        List<int>list=new List<int>(new int[numN]);
//
//        int counter=0;
//
//
//        HashSet<int>tempSet=new HashSet<int>(),totalSet=new HashSet<int>();
//        int max=numbers.Max();
//        int lastValue;
//
//        //loop through each values from left to right
//        for(int i=0;i<numN; i++)
//        {
//
//        initialJump=numbers[i];
//        counter=0;
//        //map contains total jumps for particular value then skip this loop
//        //else find total jumps
//        if(!totalSet.Contains(i))
//        {
//        //current value is greater than all values
//        if(initialJump==max)
//        {
//        totalSet.Add(i);
//        list.Insert(i,0);
//        continue;
//        }
//        lastValue=i;
//        int addValue=0;
//
//        tempSet.Clear();
//        tempSet.Add(lastValue);
//        //find total jumps
//        for(int j=i+1;j<numN; j++)
//        {
//        next=numbers[j];
//        if(initialJump<next)
//        {
//        counter++;
//
//        if(totalSet.Contains(j))
//        {
//        addValue=list[j];
//        break;
//        }
//        else
//        {
//        tempSet.Add(j);
//        initialJump=next;
//        }
//        }
//        }
//
//        //store reached values and corresponding jumps value
//        foreach(int a in tempSet)
//        {
//        totalSet.Add(a);
//        list[a]=counter+addValue;
//        counter--;
//        }
//        }
//        }
//        return list;
//
//        }


// Mogi Solutions

//static List<int> fast(string inputNumbers) {
//        var numbers=inputNumbers.Trim().Split().Select(int.Parse).ToArray();
//        int numN=numbers.Count(),initialJump=0,next=0;
//
//        List<int>list=new List<int>(new int[numN]);
//        Stack<int> stack = new Stack<int>();
//        for(int i=numN-1;i>=0;i--) {
//            while(stack.Count() > 0 && stack.Peek() <= numbers[i]) {
//                stack.Pop();
//        }
//            list.Add(stack.Count());
//            stack.Push(numbers[i]);
//        }
//        list.Reverse();
//        return list
//        }


//static new List<int> fast(String inputNumbers){
//        var numbers = inputNumbers.Trim().Split().Select(int.Parse).ToArray();
//        int numN = numbers.Length;
//        var jumps = new int[numN];
//        for (int i = 0; i < numN; i++){
//        jumps[i] = 0;
//        for (int j = i - 1; j >= 0; j--)
//        {
//        if (numbers[j] < numbers[i])
//        {
//        jumps[i] = Math.Max(jumps[i], jumps[j]);
//        }
//        }
//        jumps[i]++;
//        }
//        return jumps.ToList();
//        }
