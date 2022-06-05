using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace ConsoleApp2
{
    /// <summary>
    /// 引数を2つ受け取り、指定秒後(引数1)に、指定コード(引数2)で終了する
    /// 引数1:0-30、引数が指定されていない場合はランダムな値
    /// 引数2:0-255、引数が指定されていない場合はランダムな値
    /// /// </summary>
    class Program
    {
        private static readonly NLog.Logger _logger = NLog.LogManager.GetCurrentClassLogger();
        static int Main(string[] args)
        {
            _logger.Debug("START!!");
            Random r2 = new System.Random();
            Random r1 = new System.Random(r2.Next());
            int time = r1.Next(0, 31);
            int returncode = r1.Next(0, 256);

            if (args.Length >= 2)
            {
                try
                {
                    time = Int32.Parse(args[0]);
                    returncode = Int32.Parse(args[1]);
                }
                catch (Exception)
                {
                    _logger.Debug("Parse Error!!");
                }
            }
            Thread.Sleep(time * 1000);
            _logger.Debug("Sleep:"+ time + ", Return:" + returncode);
            _logger.Debug("END!!");
            return returncode;
        }
    }
}
