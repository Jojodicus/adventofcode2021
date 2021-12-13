using System;
using System.IO;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace aoc13
{
    public partial class Form1 : Form
    {
        private char[,] origami; // [y, x]
        private char filler;

        public Form1()
        {
            InitializeComponent();
        }

        private void Form1_Load(object sender, EventArgs e)
        {
            filler = '.';
        }

        private void bfile_Click(object sender, EventArgs e)
        {
            // open dialog
            var fd = openFileDialog1.ShowDialog();
            if (fd.Equals(DialogResult.OK))
            {
                // open file
                var filestream = openFileDialog1.OpenFile();

                // read data
                string data;
                using (var sr = new StreamReader(filestream))
                {
                    data = sr.ReadToEnd();
                }

                // parse
                parseInput(data);
            }
        }

        private void bclipboard_Click(object sender, EventArgs e)
        {
            var data = Clipboard.GetText();
            parseInput(data);
        }

        private void parseInput(string input)
        {
            // two parts - positions and folding
            var parts = input.Split("\r\n\r\n");

            // position //

            var positions = parts[0].Split("\r\n");

            // size of holding array
            int maxX = 0;
            int maxY = 0;

            // positions to add into holding array
            List<int[]> marked = new();

            foreach (string pos in positions)
            {
                var p = pos.Split(',');
                var x = int.Parse(p[0]);
                var y = int.Parse(p[1]);

                maxX = Math.Max(x, maxX);
                maxY = Math.Max(y, maxY);

                marked.Add(new int[] { x, y });
            }

            // make new array
            origami = new char[maxY + 1, maxX + 1];

            fillArray(origami);

            // fill contents
            foreach (int[] mark in marked)
            {
                origami[mark[1], mark[0]] = '#';
            }

            // show
            printContents();

            // steps //

            rtbsteps.Text = parts[1];
        }

        private void fillArray(char[,] a)
        {
            // why is there not a library method for this?
            for (int i = 0; i < a.GetLength(0); i++)
            {
                for (int j = 0; j < a.GetLength(1); j++)
                {
                    a[i, j] = '.';
                }
            }
        }

        private void printContents()
        {
            StringBuilder sb = new();
            uint count = 0;

            for (int i = 0; i < origami.GetLength(0); i++)
            {
                for (int j = 0; j < origami.GetLength(1); j++)
                {
                    var cur = origami[i, j];

                    // show
                    if (cur == '#')
                    {
                        sb.Append(cur);
                        // count
                        count++;
                    }
                    else
                    {
                        sb.Append(filler);
                    }
                }
                sb.Append('\n');
            }

            // print
            rtbdata.Text = sb.ToString();
            label1.Text = string.Format("number of dots: {0}", count);
        }

        private void bforward_Click(object sender, EventArgs e)
        {
            var txt = rtbsteps.Lines;

            // rudimentry skip of empty box or only \n
            if (txt.Length == 0 || txt[0].Length == 0 || txt[0][0] != 'f')
            {
                return;
            }

            // info
            var current = txt[0].Split('=');

            // update
            rtbsteps.Lines = txt.TakeLast(txt.Length - 1).ToArray();

            // fold
            fold(current[0][^1], int.Parse(current[1]));
        }

        private void fold(char direction, int position)
        {
            if (direction == 'y')
            {
                var newOrigami = new char[position, origami.GetLength(1)];

                // copy old data
                for (int i = 0; i < position; i++)
                {
                    for (int j = 0; j < origami.GetLength(1); j++)
                    {
                        newOrigami[i, j] = origami[i, j];
                    }
                }

                // fold
                for (int i = position + 1; i < origami.GetLength(0); i++)
                {
                    for (int j = 0; j < origami.GetLength(1); j++)
                    {
                        char cur = origami[i, j];
                        if (cur == '#')
                        {
                            newOrigami[position - (i - position), j] = cur;
                        }
                    }
                }

                // update
                origami = newOrigami;
                printContents();
            }
            else // direction == 'x'
            {
                var newOrigami = new char[origami.GetLength(0), position];

                // copy old data
                for (int i = 0; i < origami.GetLength(0); i++)
                {
                    for (int j = 0; j < position; j++)
                    {
                        newOrigami[i, j] = origami[i, j];
                    }
                }

                // fold
                for (int i = 0; i < origami.GetLength(0); i++)
                {
                    for (int j = position + 1; j < origami.GetLength(1); j++)
                    {
                        char cur = origami[i, j];
                        if (cur == '#')
                        {
                            newOrigami[i, position - (j - position)] = cur;
                        }
                    }
                }

                // update
                origami = newOrigami;
                printContents();
            }
        }

        private void checkBox1_CheckedChanged(object sender, EventArgs e)
        {
            if (checkBox1.Checked)
            {
                filler = ' ';
            }
            else
            {
                filler = '.';
            }

            // update
            printContents();
        }
    }
}
