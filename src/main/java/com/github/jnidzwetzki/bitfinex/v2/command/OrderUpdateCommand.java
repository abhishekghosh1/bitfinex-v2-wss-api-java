/*******************************************************************************
 *
 *    Copyright (C) 2015-2018 Jan Kristof Nidzwetzki
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License. 
 *
 *******************************************************************************/
package com.github.jnidzwetzki.bitfinex.v2.command;

import com.github.jnidzwetzki.bitfinex.v2.BitfinexWebsocketClient;
import com.github.jnidzwetzki.bitfinex.v2.entity.BitfinexNewOrder;
import com.github.jnidzwetzki.bitfinex.v2.entity.BitfinexSubmittedOrder;
import com.github.jnidzwetzki.bitfinex.v2.exception.BitfinexCommandException;
import org.json.JSONObject;

public class OrderUpdateCommand implements BitfinexOrderCommand {

    private final BitfinexSubmittedOrder bitfinexSubmittedOrder;

    public OrderUpdateCommand(final BitfinexSubmittedOrder bitfinexSubmittedOrder) {
        this.bitfinexSubmittedOrder = bitfinexSubmittedOrder;
    }

    @Override
    public String getCommand(final BitfinexWebsocketClient client) throws BitfinexCommandException {
        final JSONObject orderJson = new JSONObject();
        orderJson.put("id", bitfinexSubmittedOrder.getOrderId());
        orderJson.put("amount", bitfinexSubmittedOrder.getAmount().toString());
        if (bitfinexSubmittedOrder.getPrice() != null) {
            orderJson.put("price", bitfinexSubmittedOrder.getPrice().toString());
        }
        if (bitfinexSubmittedOrder.getPriceTrailing() != null) {
            orderJson.put("price_trailing", bitfinexSubmittedOrder.getPriceTrailing().toString());
        }
        if (bitfinexSubmittedOrder.getPriceAuxLimit() != null) {
            orderJson.put("price_aux_limit", bitfinexSubmittedOrder.getPriceAuxLimit().toString());
        }
        if (!bitfinexSubmittedOrder.getOrderFlags().isEmpty()) {
            orderJson.put("flags", bitfinexSubmittedOrder.getCombinedFlags());
        }
        if (bitfinexSubmittedOrder.getDelta() != null) {
            orderJson.put("delta", bitfinexSubmittedOrder.getDelta().toString());
        }
        bitfinexSubmittedOrder.getClientGroupId().ifPresent(groupId -> orderJson.put("gid", bitfinexSubmittedOrder.getClientGroupId().get()));
        return "[0, \"ou\", null, " + orderJson.toString() + "]";
    }

}
