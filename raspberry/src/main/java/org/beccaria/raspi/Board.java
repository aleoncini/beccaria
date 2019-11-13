package org.beccaria.raspi;

import com.pi4j.io.gpio.*;
import com.pi4j.system.SystemInfo;

import javax.inject.Named;
import javax.inject.Singleton;

@Singleton
@Named("board")
public class Board {

    GpioPinDigitalOutput[] pins;
    boolean isReady = false;

    Board(){
        System.out.println("==========> [Board] singleton instance initialization...");
        init();
        System.out.println("==========> [Board] ready");
    }

    void init(){
        try{
            GpioController gpio = GpioFactory.getInstance();

            pins = new GpioPinDigitalOutput[4];
            pins[0]= gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01,"pin_01", PinState.LOW);
            pins[0].setShutdownOptions(true, PinState.LOW);
            pins[1]= gpio.provisionDigitalOutputPin(RaspiPin.GPIO_03,"pin_03",PinState.LOW);
            pins[1].setShutdownOptions(true, PinState.LOW);
            pins[2]= gpio.provisionDigitalOutputPin(RaspiPin.GPIO_05,"pin_05", PinState.LOW);
            pins[2].setShutdownOptions(true, PinState.LOW);
            pins[3]= gpio.provisionDigitalOutputPin(RaspiPin.GPIO_07,"pin_07",PinState.LOW);
            pins[3].setShutdownOptions(true, PinState.LOW);
            isReady = true;
        }catch (Error err){
            //logger.error("[Raspberry Pi Board] Initialization error. May be the WiringPi native library is missing.");
        }
    }

    public String serial() {
        if (pins == null){
            return "NAN";
        }
        try {
            return SystemInfo.getSerial().toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "NAN";
    }

    public void shutdown() {
        if (pins == null){
            return;
        }
        for(int i = 0; i < pins.length; i++){
            this.off(i);
        }
    }

    public int status(int gpio) {
        if (pins == null){
            return -1;
        }
        if (gpio >= pins.length){
            return -1;
        }
        if (pins[gpio] == null){
            return -1;
        }
        return pins[gpio].getState().getValue();
    }

    public int on(int gpio) {
        if (pins == null){
            return -1;
        }
        if (gpio >= pins.length){
            return -1;
        }
        if (pins[gpio] == null){
            return -1;
        }
        if (pins[gpio].isLow()){
            pins[gpio].high();
        }
        return pins[gpio].getState().getValue();
    }

    public int off(int gpio) {
        if (pins == null){
            return -1;
        }
        if (gpio >= pins.length){
            return -1;
        }
        if (pins[gpio] == null){
            return -1;
        }
        if (pins[gpio].isHigh()){
            pins[gpio].low();
        }
        return pins[gpio].getState().getValue();
    }

}
