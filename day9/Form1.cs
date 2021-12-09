using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace aoc9
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }

        private void splitContainer1_Panel1_Paint(object sender, PaintEventArgs e)
        {

        }

        private void button1_Click(object sender, EventArgs e)
        {
            int[,] data = parseInput(richTextBox1.Text);

            int p1 = puzzleOne(data);
            int p2 = puzzleTwo(data);

            richTextBox1.Text = "puzzle 1: " + p1 + "\r\n" + "puzzle 2: " + p2;
        }

        private int[,] parseInput(String input)
        {
            String[] lines = input.Split("\n");
            int[,] res = new int[lines.Length, lines[0].Length];

            for (int i = 0; i < lines.Length; i++)
            {
                String cur = lines[i];
                for (int j = 0; j < cur.Length; j++)
                {
                    res[i, j] = cur[j] - '0';
                }
            }

            return res;
        }

        private int puzzleOne(int[,] data)
        {
            int risk = 0;

            foreach (int[] basin in basins(data))
            {
                risk += data[basin[0], basin[1]] + 1;
            }

            return risk;
        }

        private int puzzleTwo(int[,] data)
        {
            List<int[]> bs = basins(data);
            List<int> sizes = new();

            foreach (int[] basin in bs)
            {
                sizes.Add(basinSize(basin, data));
            }

            // 3 biggest sizes
            int m1 = sizes.Max();
            sizes.Remove(m1);
            int m2 = sizes.Max();
            sizes.Remove(m2);
            int m3 = sizes.Max();

            return m1 * m2 * m3;
        }

        private List<int[]> basins(int[,] data)
        {
            List<int[]> bs = new();

            for (int i = 0; i < data.GetLength(0); i++)
            {
                for (int j = 0; j < data.GetLength(1); j++)
                {
                    int cur = data[i, j];

                    if (i != 0 && cur >= data[i - 1, j]) // up
                    {
                        continue;
                    }

                    if (i + 1 != data.GetLength(0) && cur >= data[i + 1, j]) // down
                    {
                        continue;
                    }

                    if (j != 0 && cur >= data[i, j - 1]) // left
                    {
                        continue;
                    }

                    if (j + 1 != data.GetLength(1) && cur >= data[i, j + 1]) // right
                    {
                        continue;
                    }

                    bs.Add(new int[] { i, j });
                }
            }

            return bs;
        }

        private int basinSize(int[] basin, int[,] data)
        {
            Queue<int[]> queue = new();
            List<int[]> visited = new();

            visited.Add(basin);
            queue.Enqueue(basin);

            while (queue.Count != 0)
            {
                int[] v = queue.Dequeue();

                foreach (int[] neighbour in highNeighbours(v, data))
                {
                    if (!visited.Any(p => p.SequenceEqual(neighbour)))
                    {
                        visited.Add(neighbour);
                        queue.Enqueue(neighbour);
                    }
                }
            }

            return visited.Count;
        }

        private List<int[]> highNeighbours(int[] pos, int[,] data)
        {
            List<int[]> neighbours = new();

            int i = pos[0];
            int j = pos[1];

            int value = data[pos[0], pos[1]];

            if (i != 0 && value < data[i - 1, j] && data[i - 1, j] != 9)
            {
                neighbours.Add(new int[] { i - 1, j });
            }

            if (i + 1 != data.GetLength(0) && value < data[i + 1, j] && data[i + 1, j] != 9)
            {
                neighbours.Add(new int[] { i + 1, j });
            }

            if (j != 0 && value < data[i, j - 1] && data[i, j - 1] != 9)
            {
                neighbours.Add(new int[] { i, j - 1 });
            }

            if (j + 1 != data.GetLength(1) && value < data[i, j + 1] && data[i, j + 1] != 9)
            {
                neighbours.Add(new int[] { i, j + 1 });
            }

            return neighbours;
        }
    }
}
