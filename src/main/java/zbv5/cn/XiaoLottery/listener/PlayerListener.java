package zbv5.cn.XiaoLottery.listener;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.form.window.FormWindow;
import cn.nukkit.form.window.FormWindowCustom;
import org.json.JSONObject;
import zbv5.cn.XiaoLottery.Main;
import zbv5.cn.XiaoLottery.lang.Lang;
import zbv5.cn.XiaoLottery.util.FileUtil;
import zbv5.cn.XiaoLottery.util.LotteryUtil;
import zbv5.cn.XiaoLottery.util.PrintUtil;
import zbv5.cn.XiaoLottery.util.VaultUtil;
import zbv5.cn.XiaoLottery.windows.LotteryWindows;

import java.util.regex.Pattern;

public class PlayerListener implements Listener
{
    @EventHandler
    public void onClickWindow(PlayerFormRespondedEvent e)
    {
        if (e.getPlayer() == null)
        {
            return;
        }
        if (e.getResponse() == null)
        {
            return;
        }
        FormWindow gui = e.getWindow();
        JSONObject json = new JSONObject(e.getWindow().getJSONData());

        Player p = e.getPlayer();
        String title = json.getString("title");

        if (gui instanceof FormWindowCustom)
        {
            if(title.startsWith(PrintUtil.cc("&4&l彩票系统")))
            {
                String Input = ((FormWindowCustom) e.getWindow()).getResponse().getInputResponse(0);
                Pattern pattern = Pattern.compile("[0-9]*");
                if((pattern.matcher(Input).matches()))
                {
                    LotteryUtil.BuyTicket(p,Integer.parseInt(Input));
                } else {
                    PrintUtil.PrintCommandSender(p, Lang.NoInteger);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e)
    {
        final Player p = e.getPlayer();
        if(FileUtil.task.isEmpty())  return;
        for(final String task:FileUtil.task)
        {
            if(task.contains(p.getName()))
            {

                Main.getInstance().getServer().getScheduler().scheduleDelayedTask(Main.getInstance(), new Runnable()
                {
                    public void run()
                    {
                        if(p.isOnline())
                        {
                            String[] format = task.split(",");
                            if(format.length == 2)
                            {
                                double money = Double.parseDouble(format[1]);
                                VaultUtil.addMoney(p,money);
                                FileUtil.task.remove(task);
                                FileUtil.config.set("task",FileUtil.task);
                                FileUtil.config.save();
                                PrintUtil.PrintConsole("&e> &a为玩家"+p.getName()+"补偿"+money+" &7(彩票系统)");
                            } else {
                                PrintUtil.PrintConsole("&e> &c为玩家"+p.getName()+"补偿失败,玩家已离线 &7(彩票系统)");
                            }
                        }
                    }
                }, 600);


            }
        }
    }
}
