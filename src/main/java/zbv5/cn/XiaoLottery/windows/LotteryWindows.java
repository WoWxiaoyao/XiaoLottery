package zbv5.cn.XiaoLottery.windows;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.window.FormWindowCustom;
import zbv5.cn.XiaoLottery.util.FileUtil;
import zbv5.cn.XiaoLottery.util.LotteryUtil;
import zbv5.cn.XiaoLottery.util.PrintUtil;
import zbv5.cn.XiaoLottery.util.VaultUtil;

public class LotteryWindows
{
    public static FormWindowCustom Windows(Player p)
    {
        FormWindowCustom window= new FormWindowCustom(PrintUtil.cc("&4&l彩票系统"));

        StringBuilder Text = new StringBuilder();
        for(String line: FileUtil.config.getStringList("Text"))
        {
            Text.append(line).append("\n&r");
        }
        window.addElement(new ElementInput(PrintUtil.cc(Text.toString()
                .replace("{player}",p.getName())
                .replace("{money}",String.valueOf(LotteryUtil.TicketCost))
                .replace("{player_money}",String.valueOf(VaultUtil.getMoney(p)))
                .replace("{amount}",String.valueOf(LotteryUtil.getBuyTicket(p)))
                .replace("{max}",String.valueOf(LotteryUtil.MaxTickets)).replace("{money_pool}",String.valueOf(LotteryUtil.Money))), "输入购买数量", ""));
        return window;
    }
}
