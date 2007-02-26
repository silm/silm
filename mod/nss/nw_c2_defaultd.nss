#include "_gen"


const string
TORCH = "nw_it_torch001";

void hb();

void main() {
	// enter desired behaviour here

	switch ( GetUserDefinedEventNumber() ) {
		case EVENT_HEARTBEAT:
			hb();
			break;
	}
}


void hb() {
	string sTag = GetTag(OBJECT_SELF);


	// Make all guard of the legion equip torches at night
	if ( GetStringLeft(sTag, 11) == "sl_waechter" ) {
		if ( !GetLocalInt(GetModule(), "sl_torches") )
			return;

		object oTorch = GetItemPossessedBy(OBJECT_SELF, TORCH);

		if ( !GetIsObjectValid(oTorch) )
			oTorch = CreateItemOnObject(TORCH, OBJECT_SELF, 1);
		if ( !GetIsObjectValid(oTorch) ) {
			dbg("Eeerk, cannot create torch '" + TORCH + "', bailing.", 1, OBJECT_SELF);
			return;
		}

		object oTorchSlot = GetItemInSlot(INVENTORY_SLOT_LEFTHAND);

		if ( GetIsNight() && GetStringLowerCase(GetTag(oTorchSlot)) != TORCH ) {
			dbg("Equipping torch.", 5, OBJECT_SELF);
			ActionEquipItem(oTorch, INVENTORY_SLOT_LEFTHAND);
			return;
		}

		if ( GetIsDay() && GetStringLowerCase(GetTag(oTorchSlot)) == TORCH ) {
			dbg("Unequipping torch.", 5, OBJECT_SELF);
			ActionUnequipItem(oTorchSlot);
			return;
		}

		return;
	}

}
